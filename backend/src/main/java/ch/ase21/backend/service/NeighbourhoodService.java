package ch.ase21.backend.service;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;

import java.io.IOException;
import java.util.List;

public class NeighbourhoodService {

  private NeighbourhoodService() {/* void */}

  /**
   * Calculates a score for a given neighbourhood.
   * Taken into consideration are:
   * the average Revenue of Airbnb properties in the neighbourhood and
   * the average sale price of Properties in the neighbourhood
   * @param neighbourhood The neighbourhood to be evaluated.
   * @return A quality score for the neighbourhood.
   * @throws IOException Communication to the API failed.
   */
  public static Float
  neighbourhoodRating(String neighbourhood) throws IOException{
    List<Airbnb> airbnbs = AirbnbAPI.getAllByNeighbourhood(neighbourhood);
    List<Sale> sales = SalesAPI.getAllByNeighbourhood(neighbourhood);
    return NeighbourhoodService.calculateScore(airbnbs, sales);
  }

  /**
   * Calculates a score for the neighbourhood of the given airbnbs and sale
   * properties.
   * @param airbnbs The list of all airbnbs in the neighbourhood.
   * @param sales The list of all sale properties in the neighbourhood.
   * @return The score for the neighbourhood or null.
   */
  public static Float
  calculateScore(List<Airbnb> airbnbs, List<Sale> sales) {
    // Revenue per Night
    Integer averageRevenuePerNight = AirbnbService.averageRevenuePerNight(airbnbs, true);
    if(averageRevenuePerNight == null){
      return null;
    }

    // Price per residential unit
    float priceSummed = 0;
    var countSales = 0;

    for(Sale saleProperty: sales){
      Integer salePrice = saleProperty.getSalePrice();

      if(salePrice > 1000 && saleProperty.getResidentialUnits() > 0 && saleProperty.getTotalUnits() > 0){
        priceSummed += salePrice / (float) saleProperty.getTotalUnits();
        countSales++;
      }
    }

    if(countSales == 0){
      return null;
    }

    float averagePricePerApartment = priceSummed / countSales;

    // Score
    float breakEven =  averagePricePerApartment / (averageRevenuePerNight * 365);
    float score = 7 / breakEven; // Score of 1.0 if break even is in 7 years or less
    if(score > 1){
      return 1.0f;
    }
    return score;
  }
}
