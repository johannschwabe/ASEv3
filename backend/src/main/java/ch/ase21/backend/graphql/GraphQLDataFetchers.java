package ch.ase21.backend.graphql;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Property;
import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {


  public DataFetcher<Iterable<Property>> getAllAirbnbPropertiesDataFetcher() {
    return dataFetchingEnvironment -> AirbnbAPI.getAllProperties();
  }

  public DataFetcher<Iterable<Property>> getAllSalesPropertiesDataFetcher() {
    return dataFetchingEnvironment -> SalesAPI.getAllProperties();
  }
}
