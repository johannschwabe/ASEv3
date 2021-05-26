package ch.ase21.backend.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

  private GraphQL graphQL;

  @Bean
  public GraphQL graphQL() {
    return graphQL;
  }

  @PostConstruct
  public void init() throws IOException{
    URL url = Resources.getResource("schema.graphqls");
    String sdl = Resources.toString(url, Charsets.UTF_8);
    GraphQLSchema graphQLSchema = buildSchema(sdl);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  @Autowired
  GraphQLDataFetchers graphQLDataFetchers;

  private GraphQLSchema buildSchema(String sdl) {
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
    RuntimeWiring runtimeWiring = buildWiring();
    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type(newTypeWiring("Query")
            .dataFetcher("allAirbnbCoordinates", graphQLDataFetchers.getAllAirbnbCoordinatesDataFetcher())
            .dataFetcher("allSalesCoordinates", graphQLDataFetchers.getAllSalesCoordinatesDataFetcher())
            .dataFetcher("airbnbById", graphQLDataFetchers.getAirbnbByIdDataFetcher())
            .dataFetcher("saleById", graphQLDataFetchers.getSaleByIdDataFetcher())
            .dataFetcher("saleByCoordinatesId", graphQLDataFetchers.getSaleByCoordinatesIdDataFetcher())
            .dataFetcher("estimatedSalePriceById", graphQLDataFetchers.getEstimatedSalePriceByIdDataFetcher())
            .dataFetcher("salesByNeighbourhood", graphQLDataFetchers.getSalesByNeighbourhoodDataFetcher())
            .dataFetcher("airbnbsByNeighbourhood", graphQLDataFetchers.getAirbnbsByNeighbourhoodDataFetcher())
            .dataFetcher("neighbourhoodRating", graphQLDataFetchers.getNeighbourhoodRatingDataFetcher())
            .dataFetcher("calculateBreakEven", graphQLDataFetchers.calculateBreakEvenDataFetcher())
            .dataFetcher("calculatePropertyScore", graphQLDataFetchers.calculatePropertyScoreDataFetcher())
            .dataFetcher("estimatedPriceByNeighbourhood", graphQLDataFetchers.getRevenueByNeighbourhoodDataFetcher())
            .dataFetcher("allScores", graphQLDataFetchers.getAllScoresDataFetcher()))
        .type(newTypeWiring("Sale")
            .dataFetcher("score", graphQLDataFetchers.getSalePropertyScoreDataFetcher()))
        .build();
  }
}
