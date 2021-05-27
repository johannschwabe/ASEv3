//package ch.ase21.backend.service;
//
//import ch.ase21.backend.entity.Airbnb;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class AirbnbServiceTests {
//
//  /**
//   * Calculate the average revenue over all airbnb properties.
//   */
//  @Test void averageRevenuePerNight() {
//    List<Airbnb> airbnbs = new ArrayList<>();
//
//    Airbnb airbnb1 = new Airbnb("1");
//    airbnb1.setPrice(200);
//    airbnb1.setRoomType(Airbnb.APARTMENT_ROOM_TYPE);
//    airbnbs.add(airbnb1);
//
//    Airbnb airbnb2 = new Airbnb("2");
//    airbnb2.setPrice(100);
//    airbnb2.setRoomType(Airbnb.PRIVATE_ROOM_TYPE);
//    airbnbs.add(airbnb2);
//
//    Integer averageRevenue = AirbnbService.averageRevenuePerNight(airbnbs, false);
//    Assertions.assertEquals(150, averageRevenue);
//  }
//
//  /**
//   * Calculate average revenue with no airbnb properties
//   */
//  @Test void averageRevenuePerNightNoProperties() {
//    List<Airbnb> airbnbs = new ArrayList<>();
//
//    Integer averageRevenue = AirbnbService.averageRevenuePerNight(airbnbs, false);
//    Assertions.assertNull(averageRevenue);
//  }
//
//  /**
//   * Calculate the average revenue over full apartments.
//   */
//  @Test void averageRevenuePerNightFullApartment() {
//    List<Airbnb> airbnbs = new ArrayList<>();
//
//    Airbnb airbnb1 = new Airbnb("1");
//    airbnb1.setPrice(200);
//    airbnb1.setRoomType(Airbnb.APARTMENT_ROOM_TYPE);
//    airbnbs.add(airbnb1);
//
//    Airbnb airbnb2 = new Airbnb("2");
//    airbnb2.setPrice(100);
//    airbnb2.setRoomType(Airbnb.PRIVATE_ROOM_TYPE);
//    airbnbs.add(airbnb2);
//
//    Integer averageRevenue = AirbnbService.averageRevenuePerNight(airbnbs, true);
//    Assertions.assertEquals(200, averageRevenue);
//  }
//
//  /**
//   * Calculate the average revenue over full apartments with only privat rooms.
//   */
//  @Test void averageRevenuePerNightFullApartmentNoValid() {
//    List<Airbnb> airbnbs = new ArrayList<>();
//
//    Airbnb airbnb1 = new Airbnb("1");
//    airbnb1.setPrice(200);
//    airbnb1.setRoomType(Airbnb.PRIVATE_ROOM_TYPE);
//    airbnbs.add(airbnb1);
//
//    Airbnb airbnb2 = new Airbnb("2");
//    airbnb2.setPrice(100);
//    airbnb2.setRoomType(Airbnb.PRIVATE_ROOM_TYPE);
//    airbnbs.add(airbnb2);
//
//    Integer averageRevenue = AirbnbService.averageRevenuePerNight(airbnbs, true);
//    Assertions.assertNull(averageRevenue);
//  }
//}
