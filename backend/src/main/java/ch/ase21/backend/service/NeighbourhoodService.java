package ch.ase21.backend.service;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;

import java.io.IOException;

public class NeighbourhoodService {

  /**
   * Calculates a score for a given neighbourhood
   * Taken into consideration are:
   *    - average Revenue of Airbnb properties in the neighbourhood
   *    - average sale price of Properties in the neighbourhood
   * @param neighbourhood The neighbourhood to be evaluated
   * @return A quality score for the neighbourhood
   * @throws IOException Communication to the API failed.
   */
  public static Float neighbourhoodRating(String neighbourhood) throws IOException{
    var airbnbs = AirbnbAPI.getAllByNeighbourhood(neighbourhood);
    var sales = SalesAPI.getAllByNeighbourhood(neighbourhood);

    float revenueSummed = 0;
    int countAirbnb = 0;

    for(Airbnb bnb: airbnbs){
      if(bnb.getPrice() > 0){
        revenueSummed += bnb.getPrice();
        countAirbnb++;
      }
    }
    float averageRevenuePerNight = revenueSummed / countAirbnb;

    int countSales = 0;
    float priceSummed = 0;
    for(Sale individualSale: sales){
      try {
        int salesPrice = Integer.parseInt(individualSale.getSalePrice());
        if(salesPrice > 1000 && individualSale.getResidentialUnits() > 0){
          priceSummed += salesPrice / (float) individualSale.getResidentialUnits();
          countSales++;
        }
      } catch(NumberFormatException e){
        continue;
      }
    }
    float averagePricePerApartment = priceSummed / countSales;
    float breakEven =  averagePricePerApartment / (averageRevenuePerNight * 365);

    float score = 7 / breakEven;
    if(score > 1) {
      return 1.0f;
    }
    return score;
  }
}
