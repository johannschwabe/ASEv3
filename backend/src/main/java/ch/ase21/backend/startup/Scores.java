package ch.ase21.backend.startup;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;
import ch.ase21.backend.entity.Score;
import ch.ase21.backend.service.SaleService;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Scores {
  private static final Map<String, Double> propertyScores = new HashMap<>();
  private static final Map<String, List<Sale>> neighbourhoodSales = new HashMap<>();
  private static final Map<String, List<Airbnb>> neighbourhoodAirbnbs = new HashMap<>();

  public static void computeScores(){
    try {
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
      for (String neighbourhood : neighbourhoodSales.keySet()) {
        neighbourhoodAirbnbs.put(neighbourhood, AirbnbAPI.getAllByNeighbourhood(neighbourhood));
      }

      for (Sale sale : sales) {
        String neighbourhood = sale.getNeighbourhood();
        Double score = SaleService.calculatePropertyScore(sale,
            neighbourhoodSales.get(neighbourhood),
            neighbourhoodAirbnbs.get(neighbourhood));
        propertyScores.put(sale.getId(), score);
      }
    } catch (IOException e){
      System.out.println(e.getMessage());
    }
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
