package ch.ase21.salesapi.graphql;

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
    var url = Resources.getResource("schema.graphqls");
    var sdl = Resources.toString(url, Charsets.UTF_8);
    var graphQLSchema = buildSchema(sdl);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  @Autowired
  GraphQLDataFetchers graphQLDataFetchers;

  private GraphQLSchema buildSchema(String sdl) {
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
    var runtimeWiring = buildWiring();
    var schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type(newTypeWiring("Query")
            .dataFetcher("propertyById", graphQLDataFetchers.getPropertyByIdDataFetcher())
            .dataFetcher("propertyByCoordinatesId", graphQLDataFetchers.getPropertyByCoordinatesIdDataFetcher())
            .dataFetcher("allCoordinates", graphQLDataFetchers.getAllCoordinatesDataFetcher()))
        .type(newTypeWiring("Property")
            .dataFetcher("coordinates", graphQLDataFetchers.getCoordinatesDataFetcher()))
        .build();
  }
}
