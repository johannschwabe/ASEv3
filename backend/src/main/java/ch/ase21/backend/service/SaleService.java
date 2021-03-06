package ch.ase21.backend.service;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

  private SaleService() {/* void */}

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
    return SaleService.calculateEstimatedSalePrice(sale, neighbourhoodSales);
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
    Integer squareFeet = sale.getGrossSquareFeet();

    if(squareFeet == 0){
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
   * @return The expected number of years to break even
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
    if(sale.getSalePrice() == 0 || sale.getGrossSquareFeet() == 0 || sale.getTotalUnits() == 0){
      throw new IllegalArgumentException("Invalid sale property.");
    }

    if(revenuePerNight == null){
      // The average price over all full apartment airbnb properties in the initial dataset
      revenuePerNight = 212;
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
      // 2.00$ per square foot per month:
      // https://www.cottagesgardens.com/the-ins-and-outs-of-maintenance-costs-in-new-york-city/
      monthlyMaintenance = 2.0;
    }

    if(mortgageRate == null){
      // 3% mortgage rate: https://www.usbank.com/home-loans/mortgage/mortgage-rates/new-york.html
      mortgageRate = 0.03;
    }

    if(mortgageRatio == null){
      // 75% of the sale price
      mortgageRatio = 0.75;
    }

    int salePrice = sale.getSalePrice();

    int totalRevenue = revenuePerNight * sale.getTotalUnits();
    double yearlyRevenue = totalRevenue * nightsPerYear * bookingRate;

    int squareFeet = sale.getGrossSquareFeet();
    double yearlyMaintenance = squareFeet * monthlyMaintenance * 12;

    double mortgageCost = salePrice * mortgageRate * mortgageRatio;

    double breakEven = salePrice / (yearlyRevenue - yearlyMaintenance - mortgageCost);

    if(breakEven < 0.0 || breakEven > 200.0) {
      breakEven = 200.0;
    }

    return breakEven;
  }

  /**
   * Function overload to calculate break-even with only revenue given.
   * @param sale The Sale sproperty to calculate for.
   * @param revenuePerNight The expected revenue per night.
   * @return he expected number of years to break even
   */
  public static Double
  calculateBreakEven(Sale sale, Integer revenuePerNight) throws IllegalArgumentException{
    return calculateBreakEven(sale,
        revenuePerNight,
        null,
        null,
        null,
        null,
        null);
  }

  /**
   * Compute a score for a sale property.
   * The score is a combination of the neighbourhood score, the estimated sale price and the
   * calculated break even point.
   * @param id The ID of the scored sale property
   * @return The score between 0 and 10
   * @throws IOException Communication to the API failed.
   * @throws IllegalArgumentException Sale property cannot be scored.
   */
  public static Double
  propertyScore(String id) throws IOException, IllegalArgumentException{
    var sale = SalesAPI.getById(id);
    String neighbourhood = sale.getNeighbourhood();

    List<Airbnb> airbnbs = AirbnbAPI.getAllByNeighbourhood(neighbourhood);
    List<Sale> sales = SalesAPI.getAllByNeighbourhood(neighbourhood);

    return calculatePropertyScore(sale, sales, airbnbs);
  }

  /**
   * Compute a score for a sale property.
   * The score is a combination of the neighbourhood score, the estimated sale price and the
   * calculated break even point.
   * @param sale The sale to be scored.
   * @param neighbourhoodSales The sale properties in the neighbourhood.
   * @param neighbourhoodAirbnbs The airbnb properties in the neighbourhood.
   * @return The score between 0 and 10
   */
  public static Double
  calculatePropertyScore(Sale sale, List<Sale> neighbourhoodSales, List<Airbnb> neighbourhoodAirbnbs){
    // Break Even Score
    double breakEvenScore;
    try{
      Integer revenuePerNight = AirbnbService.averageRevenuePerNight(neighbourhoodAirbnbs, true);
      Double breakEven = calculateBreakEven(sale, revenuePerNight);
      breakEvenScore = 20.0 / breakEven;
    }
    catch(IllegalArgumentException e){
      breakEvenScore = 1.0;
    }

    // Neighbourhood Score
    Double neighbourhoodScore = NeighbourhoodService.calculateScore(neighbourhoodAirbnbs, neighbourhoodSales);

    if(neighbourhoodScore == null){
      neighbourhoodScore = 0.5;
    } else {
      neighbourhoodScore = neighbourhoodScore / 10.0;
    }

    // Sale Price Score
    Integer estimatedSalePrice = SaleService.calculateEstimatedSalePrice(sale, neighbourhoodSales);
    Integer salePrice = sale.getSalePrice();
    double salePriceScore;

    if(estimatedSalePrice == null || salePrice == 0){
      salePriceScore = 1.0;
    } else {
      salePriceScore = (double) estimatedSalePrice / (double) salePrice;
    }

    // Calculate Score
    double score = neighbourhoodScore * breakEvenScore * salePriceScore;
    if(score > 1.0){
      score = 10.0;
    } else {
      score = Math.round(score * 100.0) / 10.0;
    }

    return score;
  }
}
