package ch.ase21.backend.communication;

import ch.ase21.backend.entity.Property;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

public class AirbnbAPI extends GraphqlAPI {

  private AirbnbAPI() {/* void */}

  public static List<Property> getAllProperties() throws IOException{
    HttpURLConnection connection = setupConnection("http://airbnbapi:8080/graphql");
    insertQuery(connection,
        "{\n" +
          "allProperties {\n" +
            "id\n" +
            "latitude\n" +
            "longitude\n" +
          "}\n" +
        "}"
    );
    JsonNode responseData = getResponseData(connection);
    Iterator<JsonNode> nodes = responseData.get("allProperties").elements();
    List<Property> properties = new ArrayList<>();
    nodes.forEachRemaining(node -> {
      String id = node.get("id").asText();
      Float latitude = node.get("latitude").floatValue();
      Float longitude = node.get("longitude").floatValue();
      properties.add(new Property(id, latitude, longitude));
    });
    return properties;
  }
}
