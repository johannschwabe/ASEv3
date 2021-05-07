package ch.ase21.airbnbapi.graphql;

import ch.ase21.airbnbapi.entity.Property;
import ch.ase21.airbnbapi.repository.PropertyRepository;
import ch.ase21.airbnbapi.util.Utility;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GraphQLDataFetchers {

  @Autowired
  PropertyRepository propertyRepository;


  public DataFetcher<Property> getPropertyByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument("id");
      return propertyRepository
          .findById(propertyId)
          .orElse(null);
    };
  }

  public DataFetcher<Iterable<Property>> getAllPropertiesDataFetcher() {
    return dataFetchingEnvironment -> propertyRepository.findAll();
  }

  public DataFetcher<Iterable<Property>> getPropertiesByNeighbourhoodDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument("neighbourhood");
      String formattedName = neighbourhood.toLowerCase();
      formattedName = Utility.capitalizeSplit(formattedName, " ");
      formattedName = Utility.capitalizeSplit(formattedName, "-");
      return propertyRepository
          .findAllByNeighbourhood(formattedName);
    };
  }
}
