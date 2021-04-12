package ch.ase21.salesapi.graphql;

import ch.ase21.salesapi.entity.Coordinates;
import ch.ase21.salesapi.entity.Property;
import ch.ase21.salesapi.repository.CoordinatesRepository;
import ch.ase21.salesapi.repository.PropertyRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

  public DataFetcher<Iterable<Property>> getAllPropertiesDataFetcher() {
    return dataFetchingEnvironment -> {
      Iterable<Coordinates> coordinatesIterable = coordinatesRepository.findAll();
      List<Property> properties = new ArrayList<>();
      coordinatesIterable.forEach((coordinates) -> {
        String[] values = coordinates.getIdSale().split("_");
        Property property = propertyRepository.findByNumberAndBorough(Integer.parseInt(values[0]), Integer.parseInt(values[1])).orElse(null);
        if (property != null) {
          property.setCoordinates(coordinates);
          properties.add(property);
        }
      });
      return properties;
    };
  }

  public DataFetcher<Coordinates> getCoordinatesDataFetcher() {
    return dataFetchingEnvironment -> {
      Property property = dataFetchingEnvironment.getSource();
      Coordinates coordinates = property.getCoordinates();
      if (coordinates != null) {
        return coordinates;
      }
      String coordinatesId = property.getNumber().toString() + "_" + property.getBorough().toString();
      return coordinatesRepository
          .findById(coordinatesId)
          .orElse(null);
    };
  }
}
