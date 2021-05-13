package ch.ase21.backend.service;

import ch.ase21.backend.entity.Sale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

class SalesServiceTests {

  /**
   * Get a list of sales with "salePrice" and "grossSquareFeet" for testing.
   * The average price per square feet is 750.
   * @return List of neighbourhood sales.
   */
  private List<Sale> getNeighbourhoodSales(){
    List<Sale> neighbourhoodSales = new ArrayList<>();

    Sale neighbor1 = new Sale("2");
    neighbor1.setGrossSquareFeet(1000);
    neighbor1.setSalePrice(500000);
    neighbourhoodSales.add(neighbor1);

    Sale neighbor2 = new Sale("3");
    neighbor2.setGrossSquareFeet(1000);
    neighbor2.setSalePrice(1000000);
    neighbourhoodSales.add(neighbor2);

    return neighbourhoodSales;
  }

  /**
   * Calculate the estimated sale price with correct inputs.
   */
  @Test void calculateEstimatedSalePrice(){
    Sale sale = new Sale("1");
    sale.setGrossSquareFeet(1000);

    List<Sale> neighbourhoodSales = this.getNeighbourhoodSales();

    Integer estimatedSalePrice = SalesService.calculateEstimatedSalePrice(sale, neighbourhoodSales);

    Assertions.assertEquals(750000, estimatedSalePrice);
    Assertions.assertNotEquals(3645, estimatedSalePrice);
  }

  /**
   * Calculate the estimated sale price with an empty neighbors list.
   * It is expected to return null.
   */
  @Test void calculateEstimatedSalePriceNoNeighbors(){
    Sale sale = new Sale("1");
    sale.setGrossSquareFeet(1000);

    List<Sale> neighbourhoodSales = new ArrayList<>();

    Integer estimatedSalePrice = SalesService.calculateEstimatedSalePrice(sale, neighbourhoodSales);

    Assertions.assertNull(estimatedSalePrice);
  }

  /**
   * Calculate the estimated sale price of a property without a valid area.
   * It is expected to return null.
   */
  @Test void calculateEstimatedSalePriceNoArea(){
    Sale sale = new Sale("1");
    sale.setGrossSquareFeet(0);

    List<Sale> neighbourhoodSales = this.getNeighbourhoodSales();

    Integer estimatedSalePrice = SalesService.calculateEstimatedSalePrice(sale, neighbourhoodSales);

    Assertions.assertNull(estimatedSalePrice);
  }

  /**
   * Calculate the break even with all default values
   */
  @Test void calculateBreakEvenMinimal(){
    Sale sale = new Sale("1");
    sale.setTotalUnits(1);
    sale.setSalePrice(1000000);
    sale.setGrossSquareFeet(1000);

    int revenue = 200;
    int nights = 365;
    double occupancyRate = 0.8;
    double maintenance = 2.5;
    double mortgageRate = 0.03;
    double mortgageRatio = 0.75;

    Double breakEven = SalesService.calculateBreakEven(sale,
        revenue,
        null,
        null,
        null,
        null,
        null);

    Double expectedRevenue = revenue * nights * occupancyRate;
    Double expectedMaintenance = 1000 * maintenance * 12;
    Double expectedMortgage = 1000000 * mortgageRate * mortgageRatio;
    Double expectedBreakEven = 1000000 / (expectedRevenue - expectedMaintenance - expectedMortgage);

    Assertions.assertEquals(expectedBreakEven, breakEven);
  }

  /**
   * Calculate the break even with all values
   */
  @Test void calculateBreakEvenFull(){
    Sale sale = new Sale("1");
    sale.setTotalUnits(1);
    sale.setSalePrice(1000000);
    sale.setGrossSquareFeet(1000);

    int revenue = 200;
    int nights = 300;
    double occupancyRate = 0.9;
    double maintenance = 2.0;
    double mortgageRate = 0.025;
    double mortgageRatio = 0.9;

    Double breakEven = SalesService.calculateBreakEven(sale,
        revenue,
        nights,
        occupancyRate,
        maintenance,
        mortgageRate,
        mortgageRatio);

    Double expectedRevenue = revenue * nights * occupancyRate;
    Double expectedMaintenance = 1000 * maintenance * 12;
    Double expectedMortgage = 1000000 * mortgageRate * mortgageRatio;
    Double expectedBreakEven = 1000000 / (expectedRevenue - expectedMaintenance - expectedMortgage);

    Assertions.assertEquals(expectedBreakEven, breakEven);
  }

  /**
   * Calculate the break even with missing price.
   */
  @Test void calculateBreakEvenMissingPrice(){
    Sale sale = new Sale("1");
    sale.setTotalUnits(1);
    sale.setSalePrice(1000000);
    sale.setGrossSquareFeet(1000);

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> SalesService.calculateBreakEven(sale,
            null,
            null,
            null,
            null,
            null,
            null));
    try{
      SalesService.calculateBreakEven(sale,
          null,
          null,
          null,
          null,
          null,
          null);
    } catch(RuntimeException e){
      Assertions.assertEquals("Missing price.", e.getMessage());
    }
  }

  /**
   * Calculate the break even with zero values in sale.
   */
  @ParameterizedTest
  @MethodSource("parametersForBreakEvenInvalidSale")
  void calculateBreakEvenInvalidSale(Integer units, Integer price, Integer area){
    Sale sale = new Sale("1");
    sale.setTotalUnits(units);
    sale.setSalePrice(price);
    sale.setGrossSquareFeet(area);

    int revenue = 200;

    Assertions.assertThrows(IllegalArgumentException.class,
        () -> SalesService.calculateBreakEven(sale,
            revenue,
            null,
            null,
            null,
            null,
            null));
    try{
      SalesService.calculateBreakEven(sale,
          revenue,
          null,
          null,
          null,
          null,
          null);
    } catch(RuntimeException e){
      Assertions.assertEquals("Invalid sale property.", e.getMessage());
    }
  }

  private static Stream<Arguments> parametersForBreakEvenInvalidSale(){
    return Stream.of(
        Arguments.of(1, 1000000, 0),
        Arguments.of(1, 0, 1000),
        Arguments.of(0, 1000000, 1000)
    );
  }
}
