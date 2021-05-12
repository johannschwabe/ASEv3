package ch.ase21.backend.service;

import ch.ase21.backend.entity.Airbnb;

import java.util.List;

public class AirbnbService {

  private AirbnbService() {/* void */}

  /**
   * Average the revenue per night of the given airbnb properties.
   * @param airbnbs A list of airbnb properties.
   * @return The average price.
   */
  public static Integer
  averageRevenuePerNight(List<Airbnb> airbnbs){
    Integer revenueSummed = 0;
    var countAirbnb = 0;

    for(Airbnb airbnbProperty: airbnbs){
      if(airbnbProperty.getPrice() > 0){
        revenueSummed += airbnbProperty.getPrice();
        countAirbnb++;
      }
    }

    if(countAirbnb == 0){
      return null;
    }

    return revenueSummed / countAirbnb;
  }
}
