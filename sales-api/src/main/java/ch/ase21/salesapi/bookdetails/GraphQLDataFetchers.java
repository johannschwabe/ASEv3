package ch.ase21.salesapi.bookdetails;

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
      Long propertyId = dataFetchingEnvironment.getArgument("id");
      return propertyRepository
          .findById(propertyId)
          .orElse(null);
    };
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
}
