package ch.ase21.backend.graphql;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Coordinates;
import ch.ase21.backend.entity.Sale;
import ch.ase21.backend.service.NeighbourhoodService;
import ch.ase21.backend.service.SalesService;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {


  public DataFetcher<Iterable<Coordinates>> getAllAirbnbCoordinatesDataFetcher() {
    return dataFetchingEnvironment -> AirbnbAPI.getAllCoordinates();
  }

  public DataFetcher<Iterable<Coordinates>> getAllSalesCoordinatesDataFetcher() {
    return dataFetchingEnvironment -> SalesAPI.getAllCoordinates();
  }

  public DataFetcher<Airbnb> getAirbnbByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument("id");
      return AirbnbAPI.getById(propertyId);
    };
  }

  public DataFetcher<Sale> getSaleByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument("id");
      return SalesAPI.getById(propertyId);
    };
  }

  public DataFetcher<Sale> getSaleByCoordinatesIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument("id");
      return SalesAPI.getByCoordinatesId(propertyId);
    };
  }

  public DataFetcher<Integer> getEstimatedSalePriceByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument("id");
      return SalesService.estimatedSalePriceById(propertyId);
    };
  }

  public DataFetcher<Iterable<Sale>> getSalesByNeighbourhoodDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument("neighbourhood");
      return SalesAPI.getAllByNeighbourhood(neighbourhood);
    };
  }

  public DataFetcher<Iterable<Airbnb>> getAirbnbsByNeighbourhoodDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument("neighbourhood");
      return AirbnbAPI.getAllByNeighbourhood(neighbourhood);
    };
  }

  public DataFetcher<Float> getNeighbourhoodRatingDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument("neighbourhood");
      return NeighbourhoodService.neighbourhoodRating(neighbourhood);
    };
  }
}
