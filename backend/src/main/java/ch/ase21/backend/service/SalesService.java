package ch.ase21.backend.service;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesService {

  private SalesService() {/* void */}

  /**
   * Return the estimated sale price for a property of the sales DB.
   * Returns null if there are no valid neighbors.
   * @param id The ID of the property.
   * @return The estimated sale price for the property.
   * @throws IOException Thrown if the communication with the sales API failed.
   * @throws NumberFormatException Thrown if the given property has no valid area.
   */
  public static Integer
  estimatedSalePriceById(String id) throws IOException, NumberFormatException{
    var sale = SalesAPI.getGrossSquareFeetAndNeighbourhoodById(id);
    List<Sale> neighbourhoodSales = SalesAPI.getAllByNeighbourhood(sale.getNeighbourhood());
    return SalesService.calculateEstimatedSalePrice(sale, neighbourhoodSales);
  }

  /**
   * Calculate the estimated sale price for the given property of the sales DB
   * and a list of all properties in the same neighbourhood.
   * @param sale The Sale object of the property with "grossSquareFeet" given.
   * @param neighbourhoodSales The sale objects of the properties in the neighbourhood with
   *                          "grossSquareFeet" and "salePrice" given.
   * @return The estimated sale price for the given sale property.
   */
  public static Integer
  calculateEstimatedSalePrice(Sale sale, List<Sale> neighbourhoodSales) throws NumberFormatException{
    List<Integer> pricesPerSquareFeet = new ArrayList<>();

    for(Sale neighbourhoodSale: neighbourhoodSales){
      Integer squareFeet = neighbourhoodSale.getGrossSquareFeet();
      Integer salePrice = neighbourhoodSale.getSalePrice();

      if(squareFeet != null && salePrice != null && squareFeet > 0 && salePrice > 1000){
        Integer pricePerSquareFeet = salePrice / squareFeet;
        pricesPerSquareFeet.add(pricePerSquareFeet);
      }
    }

    if(pricesPerSquareFeet.isEmpty()){
      return null;
    }

    Integer sum = 0;

    for(Integer price : pricesPerSquareFeet){
      sum += price;
    }

    Integer averagePricePerSquareFeet = sum / pricesPerSquareFeet.size();
    Integer squareFeet = sale.getGrossSquareFeet();

    if(squareFeet == null){
      return null;
    }

    return squareFeet * averagePricePerSquareFeet;
  }

  /**
   * Calculate the number of years it takes to break even by leasing the given sale property on airbnb.
   * @param id The id of the property to lease.
   * @param revenuePerNight The revenue generated per night (price of the airbnb). Defaults to the
   *                        average price in the neighbourhood if null.
   * @param nightsPerYear The number of nights per year this apartment is available for renting. Defaults
   *                      to 365 (whole year) if null.
   * @param bookingRate The rate to which the apartment will bo booked. Defaults to 0.8 (80%) if null.
   * @param monthlyMaintenance The monthly maintenance cost per square feet. Defaults to 2.50$ if null.
   * @param mortgageRate The mortgage rate for the sale price. Defaults to 0.03 (3%) if null.
   * @param mortgageRatio The ratio of the sale price covered by a mortgage. Default to 0.75 (75%) if null.
   * @return The expected number of years to break even or null if calculation is not possible
   * @throws IOException Communication with the API failed.
   * @throws IllegalArgumentException Invalid sale property or revenue could not be calculated.
   */
  public static Double
  breakEven(String id,
            Integer revenuePerNight,
            Integer nightsPerYear,
            Double bookingRate,
            Double monthlyMaintenance,
            Double mortgageRate,
            Double mortgageRatio) throws IOException, IllegalArgumentException
  {
    var sale = SalesAPI.getById(id);

    if(revenuePerNight == null){
      List<Airbnb> airbnbs = AirbnbAPI.getAllByNeighbourhood(sale.getNeighbourhood());
      revenuePerNight = AirbnbService.averageRevenuePerNight(airbnbs, true);
    }

    return calculateBreakEven(sale,
        revenuePerNight,
        nightsPerYear,
        bookingRate,
        monthlyMaintenance,
        mortgageRate,
        mortgageRatio);
  }

  /**
   * Calculate the number of years it takes to break even by leasing the given sale property on airbnb.
   * @param sale The property to lease.
   * @param revenuePerNight The revenue generated per night (price of the airbnb). Must not be null.
   * @param nightsPerYear The number of nights per year this apartment is available for renting. Defaults
   *                      to 365 (whole year) if null.
   * @param bookingRate The rate to which the apartment will bo booked. Defaults to 0.8 (80%) if null.
   * @param monthlyMaintenance The monthly maintenance cost per square feet. Defaults to 2.50$ if null.
   * @param mortgageRate The mortgage rate for the full sale price. Defaults to 0.03 (3%) if null.
   * @return The expected number of years to break even or null if calculation is not possible
   * @throws IllegalArgumentException Invalid sale property or missing revenue per night.
   */
  public static Double
  calculateBreakEven(Sale sale,
                     Integer revenuePerNight,
                     Integer nightsPerYear,
                     Double bookingRate,
                     Double monthlyMaintenance,
                     Double mortgageRate,
                     Double mortgageRatio) throws IllegalArgumentException
  {
    if(revenuePerNight == null){
      throw new IllegalArgumentException("Missing price.");
    }

    if(sale.getSalePrice() == 0 || sale.getGrossSquareFeet() == 0 || sale.getTotalUnits() == 0){
      throw new IllegalArgumentException("Invalid sale property.");
    }

    if(nightsPerYear == null){
      // Available all year
      nightsPerYear = 365;
    }

    if(bookingRate == null){
      // 80% of the available nights booked
      bookingRate = 0.8;
    }

    if(monthlyMaintenance == null){
      // 2.50$ per square foot per month:
      // https://www.cottagesgardens.com/the-ins-and-outs-of-maintenance-costs-in-new-york-city/
      monthlyMaintenance = 2.5;
    }

    if(mortgageRate == null){
      // 3% mortgage rate: https://www.usbank.com/home-loans/mortgage/mortgage-rates/new-york.html
      mortgageRate = 0.03;
    }

    if(mortgageRatio == null){
      // 75% of the sale price
      mortgageRatio = 0.75;
    }

    int salesPrice = sale.getSalePrice() / sale.getTotalUnits();

    double yearlyRevenue = revenuePerNight * nightsPerYear * bookingRate;

    int squareFeet = sale.getGrossSquareFeet() / sale.getTotalUnits();
    double yearlyMaintenance = squareFeet * monthlyMaintenance * 12;

    double mortgageCost = salesPrice * mortgageRate * mortgageRatio;

    return salesPrice / (yearlyRevenue - yearlyMaintenance - mortgageCost);
  }
}
