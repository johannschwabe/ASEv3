package ch.ase21.backend.communication;

import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Coordinates;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

public class AirbnbAPI extends GraphqlAPI {

  private static final String API_URL = "http://airbnbapi:8080/graphql";
//  private static final String API_URL = "http://localhost:8383/graphql";

  private static final String PROPERTY_FIELDS =
      "id\n" +
      "name\n" +
      "hostId\n" +
      "hostName\n" +
      "neighbourhoodGroup\n" +
      "neighbourhood\n" +
      "latitude\n" +
      "longitude\n" +
      "roomType\n" +
      "price\n" +
      "minimumNights\n" +
      "numberOfReviews\n" +
      "lastReview\n" +
      "reviewsPerMonth\n" +
      "calculatedHostListingsCount\n" +
      "availability365\n";


  private AirbnbAPI() {/* void */}

  /**
   * Takes a JsonNode representing an airbnb property and returns the property with all fields in the json node.
   * The JsonNode must contain all fields of an airbnb property.
   * @param node A JsonNode with all fields of an airbnb property.
   * @return The airbnb property.
   */
  private static Airbnb getAirbnbFromNode(JsonNode node){
    String propertyId = node.get("id").asText();
    String name = node.get("name").asText();
    Integer hostId = node.get("hostId").intValue();
    String hostName = node.get("hostName").asText();
    String neighbourhoodGroup = node.get("neighbourhoodGroup").asText();
    String neighbourhood = node.get("neighbourhood").asText();
    Float latitude = node.get("latitude").floatValue();
    Float longitude = node.get("longitude").floatValue();
    String roomType = node.get("roomType").asText();
    Integer price = node.get("price").intValue();
    Integer minimumNights = node.get("minimumNights").intValue();
    Integer numberOfReviews = node.get("numberOfReviews").intValue();
    String lastReview = node.get("lastReview").asText();
    Integer reviewsPerMonth = node.get("reviewsPerMonth").intValue();
    Integer calculatedHostListingsCount = node.get("calculatedHostListingsCount").intValue();
    Integer availability365 = node.get("availability365").intValue();

    return new Airbnb(
        propertyId,
        name,
        hostId,
        hostName,
        neighbourhoodGroup,
        neighbourhood,
        latitude,
        longitude,
        roomType,
        price,
        minimumNights,
        numberOfReviews,
        lastReview,
        reviewsPerMonth,
        calculatedHostListingsCount,
        availability365
    );
  }

  /**
   * Get all coordinates of the airbnb properties as Coordinate objects.
   * @return List of all coordinates.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static List<Coordinates> getAllCoordinates() throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "allProperties {\n" +
            "id\n" +
            "latitude\n" +
            "longitude\n" +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    Iterator<JsonNode> nodes = responseData.get("allProperties").elements();
    List<Coordinates> properties = new ArrayList<>();
    nodes.forEachRemaining(node -> {
      String id = node.get("id").asText();
      Float latitude = node.get("latitude").floatValue();
      Float longitude = node.get("longitude").floatValue();
      properties.add(new Coordinates(id, latitude, longitude));
    });
    return properties;
  }

  /**
   * Get an airbnb property by its ID.
   * @param id The id of the airbnb property.
   * @return The Airbnb object.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static Airbnb getById(String id) throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "propertyById(id: \"" + id + "\") {\n" +
            PROPERTY_FIELDS +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    JsonNode node = responseData.get("propertyById");
    return getAirbnbFromNode(node);
  }

  /**
   * Get all airbnb properties in the given neighbourhood.
   * @param neighbourhood The neighbourhood name.
   * @return The List of all airbnb properties in the neighbourhood.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static List<Airbnb> getAllByNeighbourhood(String neighbourhood) throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "propertiesByNeighbourhood(neighbourhood:\"" + neighbourhood + "\") {\n" +
            PROPERTY_FIELDS +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    Iterator<JsonNode> nodes = responseData.get("propertiesByNeighbourhood").elements();
    List<Airbnb> properties = new ArrayList<>();
    nodes.forEachRemaining(node -> properties.add(getAirbnbFromNode(node)));
    return properties;
  }
}
