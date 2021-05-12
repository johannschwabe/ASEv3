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

  private static final String ID = "id";
  private static final String NEIGHBOURHOOD = "neighbourhood";

  public DataFetcher<Iterable<Coordinates>> getAllAirbnbCoordinatesDataFetcher() {
    return dataFetchingEnvironment -> AirbnbAPI.getAllCoordinates();
  }

  public DataFetcher<Iterable<Coordinates>> getAllSalesCoordinatesDataFetcher() {
    return dataFetchingEnvironment -> SalesAPI.getAllCoordinates();
  }

  public DataFetcher<Airbnb> getAirbnbByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument(ID);
      return AirbnbAPI.getById(propertyId);
    };
  }

  public DataFetcher<Sale> getSaleByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument(ID);
      return SalesAPI.getById(propertyId);
    };
  }

  public DataFetcher<Sale> getSaleByCoordinatesIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument(ID);
      return SalesAPI.getByCoordinatesId(propertyId);
    };
  }

  public DataFetcher<Integer> getEstimatedSalePriceByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String propertyId = dataFetchingEnvironment.getArgument(ID);
      return SalesService.estimatedSalePriceById(propertyId);
    };
  }

  public DataFetcher<Iterable<Sale>> getSalesByNeighbourhoodDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument(NEIGHBOURHOOD);
      return SalesAPI.getAllByNeighbourhood(neighbourhood);
    };
  }

  public DataFetcher<Iterable<Airbnb>> getAirbnbsByNeighbourhoodDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument(NEIGHBOURHOOD);
      return AirbnbAPI.getAllByNeighbourhood(neighbourhood);
    };
  }

  public DataFetcher<Float> getNeighbourhoodRatingDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument(NEIGHBOURHOOD);
      return NeighbourhoodService.neighbourhoodRating(neighbourhood);
    };
  }

  public DataFetcher<Float> calculateBreakEvenDataFetcher() {
    return dataFetchingEnvironment -> {
      String id = dataFetchingEnvironment.getArgument(ID);
      Integer price = dataFetchingEnvironment.getArgument("price");
      Integer nights = dataFetchingEnvironment.getArgument("nights");
      Float rate = dataFetchingEnvironment.getArgument("rate");
      Float maintenance = dataFetchingEnvironment.getArgument("maintenance");
      Float mortgage = dataFetchingEnvironment.getArgument("mortgage");
      return SalesService.breakEven(id, price, nights, rate, maintenance, mortgage);
    };
  }
}
