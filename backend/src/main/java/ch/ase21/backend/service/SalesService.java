package ch.ase21.backend.service;

import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesService {

  private SalesService() {/* void */}

  public static Integer estimatedSalePriceById(String id) throws IOException{
    var sale = SalesAPI.getGrossSquareFeetAndNeighborhoodById(id);
    List<Sale> properties = SalesAPI.getGrossSquareFeetAndSalePriceByNeighborhood(sale.getNeighbourhood());
    List<Integer> pricesPerSquareFeet = new ArrayList<>();

    for(Sale property: properties){
      int squareFeet;
      int salePrice;
      try{
        squareFeet = Integer.parseInt(property.getGrossSquareFeet());
        salePrice = Integer.parseInt(property.getSalePrice());
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
