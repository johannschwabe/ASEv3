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
      int squareFeet;
      int salePrice;
      try{
        squareFeet = Integer.parseInt(neighbourhoodSale.getGrossSquareFeet());
        salePrice = Integer.parseInt(neighbourhoodSale.getSalePrice());
      } catch(NumberFormatException e){
        continue;
      }
      if(squareFeet > 0 && salePrice > 1000){
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
    Integer squareFeet = Integer.parseInt(sale.getGrossSquareFeet());
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
   * @param mortgageRate The mortgage rate for the full sale price. Defaults to 0.03 (3%) if null.
   * @return The expected number of years to break even or null if calculation is not possible
   * @throws IOException Communication with the API failed.
   * @throws NumberFormatException Invalid sale price or gross square feet of the sale property.
   */
  public static Float
  breakEven(String id,
            Integer revenuePerNight,
            Integer nightsPerYear,
            Float bookingRate,
            Float monthlyMaintenance,
            Float mortgageRate) throws IOException, NumberFormatException
  {
    var sale = SalesAPI.getById(id);

    if(revenuePerNight == null){
      List<Airbnb> airbnbs = AirbnbAPI.getAllByNeighbourhood(sale.getNeighbourhood());
      revenuePerNight = AirbnbService.averageRevenuePerNight(airbnbs);
    }

    return calculateBreakEven(sale, revenuePerNight, nightsPerYear, bookingRate, monthlyMaintenance, mortgageRate);
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
   * @throws NumberFormatException Invalid sale price or gross square feet of the sale property.
   */
  public static Float
  calculateBreakEven(Sale sale,
                     Integer revenuePerNight,
                     Integer nightsPerYear,
                     Float bookingRate,
                     Float monthlyMaintenance,
                     Float mortgageRate) throws NumberFormatException
  {
    if(revenuePerNight == null || sale.getResidentialUnits() == 0){
      return null;
    }

    if(nightsPerYear == null){
      // Available all year
      nightsPerYear = 365;
    }

    if(bookingRate == null){
      // 80% of the available nights booked
      bookingRate = 0.8f;
    }

    if(monthlyMaintenance == null){
      // 2.50$ per square foot per month:
      // https://www.cottagesgardens.com/the-ins-and-outs-of-maintenance-costs-in-new-york-city/
      monthlyMaintenance = 2.5f;
    }

    if(mortgageRate == null){
      // 3% mortgage rate: https://www.usbank.com/home-loans/mortgage/mortgage-rates/new-york.html
      mortgageRate = 0.03f;
    }

    int salesPrice = Integer.parseInt(sale.getSalePrice()) / sale.getTotalUnits();

    float yearlyRevenue = revenuePerNight * nightsPerYear * bookingRate;

    Integer squareFeet = Integer.parseInt(sale.getGrossSquareFeet()) / sale.getTotalUnits();
    float yearlyMaintenance = squareFeet * monthlyMaintenance * 12;

    float mortgageCost = salesPrice * mortgageRate;

    return salesPrice / (yearlyRevenue - yearlyMaintenance - mortgageCost);
  }
}
