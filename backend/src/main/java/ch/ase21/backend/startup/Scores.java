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

  @PostConstruct
  public static void computeScores(){
    new Thread(() -> {
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(10);
          System.out.println("fetch sales");
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

          System.out.println("fetch airbnbs");
          for (String neighbourhood : neighbourhoodSales.keySet()) {
            neighbourhoodAirbnbs.put(neighbourhood, AirbnbAPI.getAllByNeighbourhood(neighbourhood));
          }

          System.out.println("compute scores");
          for (Sale sale : sales) {
            String neighbourhood = sale.getNeighbourhood();
            Double score = SaleService.calculatePropertyScore(sale,
                neighbourhoodSales.get(neighbourhood),
                neighbourhoodAirbnbs.get(neighbourhood));
            propertyScores.put(sale.getId(), score);
          }

          System.out.println("scores computed");
          break;
        } catch (IOException ignored) {
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }).start();
  }

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
