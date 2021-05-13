package ch.ase21.salesapi.graphql;

import ch.ase21.salesapi.entity.Coordinates;
import ch.ase21.salesapi.entity.Property;
import ch.ase21.salesapi.repository.CoordinatesRepository;
import ch.ase21.salesapi.repository.PropertyRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GraphQLDataFetchers {

  @Autowired 
  PropertyRepository propertyRepository;
  
  @Autowired 
  CoordinatesRepository coordinatesRepository;
  
  public DataFetcher<Property> getPropertyByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument("id");
      return propertyRepository
          .findById(propertyId)
          .orElse(null);
    };
  }

  public DataFetcher<Property> getPropertyByCoordinatesIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String coordinatesId = dataFetchingEnvironment.getArgument("id");
      String[] values = coordinatesId.split("_");
      Integer number = Integer.parseInt(values[0]);
      Integer borough = Integer.parseInt(values[1]);
      return propertyRepository
          .findByNumberAndBorough(number, borough)
          .orElse(null);
    };
  }

  public DataFetcher<Iterable<Coordinates>> getAllCoordinatesDataFetcher() {
    return dataFetchingEnvironment -> coordinatesRepository.findAll();
  }

  public DataFetcher<Coordinates> getCoordinatesDataFetcher() {
    return dataFetchingEnvironment -> {
      Property property = dataFetchingEnvironment.getSource();
      String coordinatesId = property.getNumber().toString() + "_" + property.getBorough().toString();
      return coordinatesRepository
          .findById(coordinatesId)
          .orElse(null);
    };
  }

  public DataFetcher<String> getNeighbourhoodDataFetcher() {
    return dataFetchingEnvironment -> {
      Property property = dataFetchingEnvironment.getSource();
      return property.getNeighborhood();
    };
  }

  public DataFetcher<Iterable<Property>> getPropertiesByNeighbourhoodDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument("neighbourhood");
      String uppercaseNeighbourhood = neighbourhood.toUpperCase();
      return propertyRepository
          .findPropertiesByNeighborhood(uppercaseNeighbourhood);
    };
  }
  
  public DataFetcher<Integer> getLandSquareFeetDataFetcher() {
    return dataFetchingEnvironment -> {
      Property property = dataFetchingEnvironment.getSource();
      Integer landSquareFeet;
      try{ 
        landSquareFeet = Integer.parseInt(property.getLandSquareFeet());
      } catch(NumberFormatException e){
        landSquareFeet = null;
      }
      return landSquareFeet;
    };
  }

  public DataFetcher<Integer> getGrossSquareFeetDataFetcher() {
    return dataFetchingEnvironment -> {
      Property property = dataFetchingEnvironment.getSource();
      Integer grossSquareFeet;
      try{
        grossSquareFeet = Integer.parseInt(property.getGrossSquareFeet());
      } catch(NumberFormatException e){
        grossSquareFeet = null;
      }
      return grossSquareFeet;
    };
  }

  public DataFetcher<Integer> getSalePriceDataFetcher() {
    return dataFetchingEnvironment -> {
      Property property = dataFetchingEnvironment.getSource();
      Integer salePrice;
      try{
        salePrice = Integer.parseInt(property.getSalePrice());
      } catch(NumberFormatException e){
        salePrice = null;
      }
      return salePrice;
    };
  }
}
