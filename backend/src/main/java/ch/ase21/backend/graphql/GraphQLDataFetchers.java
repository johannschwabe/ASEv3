package ch.ase21.backend.graphql;

import ch.ase21.backend.communication.AirbnbAPI;
import ch.ase21.backend.communication.SalesAPI;
import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Coordinates;
import ch.ase21.backend.entity.Sale;
import ch.ase21.backend.service.NeighbourhoodService;
import ch.ase21.backend.service.SaleService;
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
      return SaleService.estimatedSalePriceById(propertyId);
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

  public DataFetcher<Double> getNeighbourhoodRatingDataFetcher() {
    return dataFetchingEnvironment -> {
      String neighbourhood = dataFetchingEnvironment.getArgument(NEIGHBOURHOOD);
      return NeighbourhoodService.neighbourhoodRating(neighbourhood);
    };
  }

  public DataFetcher<Double> calculateBreakEvenDataFetcher() {
    return dataFetchingEnvironment -> {
      String id = dataFetchingEnvironment.getArgument(ID);
      Integer price = dataFetchingEnvironment.getArgument("price");
      Integer nights = dataFetchingEnvironment.getArgument("nights");
      Double occupancyRate = dataFetchingEnvironment.getArgument("occupancyRate");
      Double maintenance = dataFetchingEnvironment.getArgument("maintenance");
      Double mortgageRate = dataFetchingEnvironment.getArgument("mortgageRate");
      Double mortgageRatio = dataFetchingEnvironment.getArgument("mortgageRatio");

      return SaleService.breakEven(id,
          price,
          nights,
          occupancyRate,
          maintenance,
          mortgageRate,
          mortgageRatio);
    };
  }

  public DataFetcher<Double> calculatePropertyScoreDataFetcher() {
    return dataFetchingEnvironment -> {
      String id = dataFetchingEnvironment.getArgument(ID);
      return SaleService.propertyScore(id);
    };
  }

  public DataFetcher<Double> getSalePropertyScoreDataFetcher() {
    return dataFetchingEnvironment -> {
      Sale sale = dataFetchingEnvironment.getContext();
      return SaleService.propertyScore(sale.getId());
    };
  }
}
