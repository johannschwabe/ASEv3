package ch.ase21.backend.service;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;
import ch.ase21.backend.entity.SaleScore;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Service
public class SaleScoreService {
  private static final Map<String, Double> propertyScores = new HashMap<>();
  private static final Map<String, Sale> allSales = new HashMap<>();
  private static final Map<String, List<Sale>> neighbourhoodSales = new HashMap<>();
  private static final Map<String, List<Airbnb>> neighbourhoodAirbnbs = new HashMap<>();

  private SaleScoreService() {/* void */}

  /**
   * Compute all property scores.
   * This function is called at startup of the backend.
   * The scores will not change until the application is restarted.
   */
  @PostConstruct
  public void computeScores(){
    // Continue startup while trying to calculate scores
    new Thread(() -> {
      while (true) {
        try {
          // Wait 10 seconds before (re-)try
          TimeUnit.SECONDS.sleep(10);

          // Fetch all properties
          List<Sale> sales = fetchAllSales();
          fetchAllAirbnbs();

          // Calculate scores
          for (Sale sale : sales) {
            String neighbourhood = sale.getNeighbourhood();
            Double score = SaleService.calculatePropertyScore(sale,
                neighbourhoodSales.get(neighbourhood),
                neighbourhoodAirbnbs.get(neighbourhood));
            propertyScores.put(sale.getId(), score);
          }

          break;
        } catch (IOException ignored) {
          // Retry
        } catch (InterruptedException e) {
          // Sleep got interrupted
          Thread.currentThread().interrupt();
        }
      }
    }).start();
  }

  /**
   * Fetch all sales and add them to the sales neighbourhood map.
   * @return The list of all fetched sales.
   * @throws IOException Communication to the API failed.
   */
  private static List<Sale> fetchAllSales() throws IOException {
    List<Sale> sales = SalesAPI.getAll();
    for (Sale sale : sales) {
      allSales.put(sale.getId(), sale);
      String neighbourhood = sale.getNeighbourhood();
      if (neighbourhoodSales.containsKey(neighbourhood)) {
        neighbourhoodSales.get(neighbourhood).add(sale);
      } else {
        List<Sale> neighbours = new ArrayList<>();
        neighbours.add(sale);
        neighbourhoodSales.put(neighbourhood, neighbours);
      }
    }
    return sales;
  }

  /**
   * Fetch all airbnbs and add them to the airbnb neighbourhood map.
   * @throws IOException Communication to the API failed.
   */
  private static void fetchAllAirbnbs() throws IOException {
    for (String neighbourhood : neighbourhoodSales.keySet()) {
      neighbourhoodAirbnbs.put(neighbourhood, AirbnbAPI.getAllByNeighbourhood(neighbourhood));
    }
  }

  /**
   * Return the List of all sale property scores.
   * The scores are sorted descending by score value (10.0 - 0.0).
   * @return A list of all scores.
   */
  public static List<SaleScore> getScores(){
    List<Entry<String, Double>> scoreEntries = new ArrayList<>(propertyScores.entrySet());
    scoreEntries.sort(Entry.comparingByValue(Collections.reverseOrder()));
    List<SaleScore> scores = new ArrayList<>();
    for(var entry: scoreEntries){
      var sale = allSales.get(entry.getKey());
      var score = entry.getValue();
      // Do not send edge cases (10.0: too good to be true, 5.0: all default values)
      if(score != 10.0 && score != 5.0){
        scores.add(
            new SaleScore(entry.getKey(),
                entry.getValue(),
                sale.getAddress(),
                sale.getSalePrice(),
                sale.getNeighbourhood()
            )
        );
      }
    }
    return scores;
  }
}
