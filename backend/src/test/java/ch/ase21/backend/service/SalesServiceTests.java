package ch.ase21.backend.service;

import ch.ase21.backend.entity.Sale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SalesServiceTests {

  /**
   * Get a list of sales with "salePrice" and "grossSquareFeet" for testing.
   * The average price per square feet is 750.
   * @return List of neighborhood sales.
   */
  private List<Sale> getNeighborhoodSales(){
    List<Sale> neighborhoodSales = new ArrayList<>();

    Sale neighbor1 = new Sale("2");
    neighbor1.setGrossSquareFeet("1000");
    neighbor1.setSalePrice("500000");
    neighborhoodSales.add(neighbor1);

    Sale neighbor2 = new Sale("3");
    neighbor2.setGrossSquareFeet("1000");
    neighbor2.setSalePrice("1000000");
    neighborhoodSales.add(neighbor2);

    return neighborhoodSales;
  }

  /**
   * Calculate the estimated sale price with correct inputs.
   */
  @Test void calculateEstimatedSalePrice(){
    Sale sale = new Sale("1");
    sale.setGrossSquareFeet("1000");

    List<Sale> neighborhoodSales = this.getNeighborhoodSales();

    Integer estimatedSalePrice = SalesService.calculateEstimatedSalePrice(sale, neighborhoodSales);

    Assertions.assertEquals(750000, estimatedSalePrice);
    Assertions.assertNotEquals(3645, estimatedSalePrice);
  }

  /**
   * Calculate the estimated sale price with an empty neighbors list.
   * It is expected to return null.
   */
  @Test void calculateEstimatedSalePriceNoNeighbors(){
    Sale sale = new Sale("1");
    sale.setGrossSquareFeet("1000");

    List<Sale> neighborhoodSales = new ArrayList<>();

    Integer estimatedSalePrice = SalesService.calculateEstimatedSalePrice(sale, neighborhoodSales);

    Assertions.assertNull(estimatedSalePrice);
  }

  /**
   * Calculate the estimated sale price of a property without a valid area.
   * It is expected to throw a NumberFormatException.
   */
  @Test void calculateEstimatedSalePriceNoArea(){
    Sale sale = new Sale("1");
    sale.setGrossSquareFeet(" - ");

    List<Sale> neighborhoodSales = this.getNeighborhoodSales();

    Assertions.assertThrows(NumberFormatException.class,
        () -> SalesService.calculateEstimatedSalePrice(sale, neighborhoodSales));
  }

}