package ch.ase21.airbnbapi.bookdetails;

import ch.ase21.airbnbapi.entity.Property;
import ch.ase21.airbnbapi.repository.PropertyRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

  @Autowired
  PropertyRepository propertyRepository;


  public DataFetcher<Property> getPropertyByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      Long propertyId = dataFetchingEnvironment.getArgument("id");
      return propertyRepository
          .findById(propertyId)
          .orElse(null);
    };
  }
}
