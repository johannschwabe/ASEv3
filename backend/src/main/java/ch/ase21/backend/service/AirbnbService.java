package ch.ase21.backend.service;

import ch.ase21.backend.entity.Airbnb;

import java.util.List;

public class AirbnbService {

  private AirbnbService() {/* void */}

  /**
   * Average the revenue per night of the given airbnb properties.
   * @param airbnbs A list of airbnb properties.
   * @param fullApartment Whether only full apartments are calculated in.
   * @return The average price.
   */
  public static Integer
  averageRevenuePerNight(List<Airbnb> airbnbs, Boolean fullApartment){
    Integer revenueSummed = 0;
    var countAirbnb = 0;

    for(Airbnb airbnbProperty: airbnbs){
      if(airbnbProperty.getPrice() > 0 && (!fullApartment || airbnbProperty.getRoomType().equals(Airbnb.APARTMENT_ROOM_TYPE))){
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
