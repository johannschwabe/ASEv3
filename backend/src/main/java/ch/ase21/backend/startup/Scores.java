package ch.ase21.backend.startup;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;
import ch.ase21.backend.entity.Score;
import ch.ase21.backend.service.SaleService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Service
public class Scores {
  private static final Map<String, Double> propertyScores = new HashMap<>();
  private static final Map<String, List<Sale>> neighbourhoodSales = new HashMap<>();
  private static final Map<String, List<Airbnb>> neighbourhoodAirbnbs = new HashMap<>();

  /**
   * Compute all property scores.
   * This function is called at startup of the backend.
   * The scores will not change until the application is restarted.
   */
  @PostConstruct
  public static void computeScores(){
    // Continue startup while trying to calculate scores
    new Thread(() -> {
      while (true) {
        try {
          // Wait 10 seconds before (re-)try
          TimeUnit.SECONDS.sleep(10);

          // Fetch all sales
          List<Sale> sales = SalesAPI.getAll();
          for (Sale sale : sales) {
            String neighbourhood = sale.getNeighbourhood();
            if (neighbourhoodSales.containsKey(neighbourhood)) {
              neighbourhoodSales.get(neighbourhood).add(sale);
            } else {
              List<Sale> neighbours = new ArrayList<>();
              neighbours.add(sale);
              neighbourhoodSales.put(neighbourhood, neighbours);
            }
          }

          // Fetch all airbnbs
          for (String neighbourhood : neighbourhoodSales.keySet()) {
            neighbourhoodAirbnbs.put(neighbourhood, AirbnbAPI.getAllByNeighbourhood(neighbourhood));
          }

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
          throw new RuntimeException(e);
        }
      }
    }).start();
  }

  /**
   * Return the List of all sale property scores.
   * The scores are sorted descending by score value (10.0 - 0.0).
   * @return A list of all scores.
   */
  public static List<Score> getScores(){
    List<Entry<String, Double>> scoreEntries = new ArrayList<>(propertyScores.entrySet());
    scoreEntries.sort(Entry.comparingByValue(Collections.reverseOrder()));
    List<Score> scores = new ArrayList<>();
    for(var entry: scoreEntries){
      scores.add(new Score(entry.getKey(), entry.getValue()));
    }
    return scores;
  }
}
