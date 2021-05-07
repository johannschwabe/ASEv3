package ch.ase21.backend.service;

import ch.ase21.backend.communication.SalesAPI;
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
  public static Integer estimatedSalePriceById(String id) throws IOException, NumberFormatException{
    var sale = SalesAPI.getGrossSquareFeetAndNeighborhoodById(id);
    List<Sale> neighborhoodSales = SalesAPI.getGrossSquareFeetAndSalePriceByNeighborhood(sale.getNeighbourhood());
    return SalesService.calculateEstimatedSalePrice(sale, neighborhoodSales);
  }

  /**
   * Calculate the estimated sale price for the given property of the sales DB
   * and a list of all properties in the same neighbourhood.
   * @param sale The Sale object of the property with "grossSquareFeet" given.
   * @param neighborhoodSales The sale objects of the properties in the neighborhood with
   *                          "grossSquareFeet" and "salePrice" given.
   * @return The estimated sale price for the given sale property.
   */
  public static Integer
  calculateEstimatedSalePrice(Sale sale, List<Sale> neighborhoodSales) throws NumberFormatException{
    List<Integer> pricesPerSquareFeet = new ArrayList<>();

    for(Sale neighborhoodSale: neighborhoodSales){
      int squareFeet;
      int salePrice;
      try{
        squareFeet = Integer.parseInt(neighborhoodSale.getGrossSquareFeet());
        salePrice = Integer.parseInt(neighborhoodSale.getSalePrice());
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
}
