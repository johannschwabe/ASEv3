package ch.ase21.backend.communication;

import ch.ase21.backend.entity.Coordinates;
import ch.ase21.backend.entity.Sale;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

public class SalesAPI extends GraphqlAPI{

  private static final String API_URL = "http://salesapi:8080/graphql";

  // Sale fields
  private static final String ID = "id";
  private static final String NEIGHBOURHOOD = "neighbourhood";
  private static final String GROSS_SQUARE_FEET = "grossSquareFeet";
  private static final String SALE_PRICE = "salePrice";

  // Coordinates
  private static final String ID_SALE = "idSale";
  private static final String LATITUDE = "latitude";
  private static final String LONGITUDE = "longitude";

  // All fields
  private static final String PROPERTY_FIELDS =
      ID + "\n" +
      "number\n" +
      "borough\n" +
      NEIGHBOURHOOD + "\n" +
      "buildingClassCategory\n" +
      "taxClassAtPresent\n" +
      "block\n" +
      "lot\n" +
      "easement\n" +
      "buildingClassAtPresent\n" +
      "address\n" +
      "apartmentNumber\n" +
      "zipCode\n" +
      "residentialUnits\n" +
      "commercialUnits\n" +
      "totalUnits\n" +
      "landSquareFeet\n" +
      GROSS_SQUARE_FEET + "\n" +
      "yearBuilt\n" +
      "taxClassAtTimeOfSale\n" +
      "buildingClassAtTimeOfSale\n" +
      SALE_PRICE + "\n" +
      "saleDate\n" +
      "coordinates{\n" +
        ID_SALE + "\n" +
        LATITUDE + "\n" +
        LONGITUDE + "\n" +
        "returnedStreetName\n" +
        "returnedZipCode\n" +
      "}\n";


  private SalesAPI() {/* void */}

  /**
   * Takes a JsonNode representing a sale property and returns the property with all fields in the json node.
   * The JsonNode must contain all fields of a sale property.
   * @param node A JsonNode with all fields of a sale property.
   * @return The sale property.
   */
  private static Sale getSaleFromNode(JsonNode node){
    String propertyId = node.get(ID).asText();
    Integer number = node.get("number").intValue();
    Integer borough = node.get("borough").intValue();
    String neighbourhood = node.get(NEIGHBOURHOOD).asText();
    String buildingClassCategory = node.get("buildingClassCategory").asText();
    String taxClassAtPresent = node.get("taxClassAtPresent").asText();
    Integer block = node.get("block").intValue();
    Integer lot = node.get("lot").intValue();
    String easement = node.get("easement").asText();
    String buildingClassAtPresent = node.get("buildingClassAtPresent").asText();
    String address = node.get("address").asText();
    String apartmentNumber = node.get("apartmentNumber").asText();
    Integer zipCode = node.get("zipCode").intValue();
    Integer residentialUnits = node.get("residentialUnits").intValue();
    Integer commercialUnits = node.get("commercialUnits").intValue();
    Integer totalUnits = node.get("totalUnits").intValue();
    String landSquareFeet = node.get("landSquareFeet").asText();
    String grossSquareFeet = node.get(GROSS_SQUARE_FEET).asText();
    Integer yearBuilt = node.get("yearBuilt").intValue();
    Integer taxClassAtTimeOfSale = node.get("taxClassAtTimeOfSale").intValue();
    String buildingClassAtTimeOfSale = node.get("buildingClassAtTimeOfSale").asText();
    String salePrice = node.get(SALE_PRICE).asText();
    String saleDate = node.get("saleDate").asText();
    // Coordinates
    JsonNode coordinates = node.get("coordinates");
    String idSale;
    Float latitude;
    Float longitude;
    String returnedStreetName;
    Integer returnedZipCode;

    if(coordinates != null){
      idSale = coordinates.get(ID_SALE).asText();
      latitude = coordinates.get(LATITUDE).floatValue();
      longitude = coordinates.get(LONGITUDE).floatValue();
      returnedStreetName = coordinates.get("returnedStreetName").asText();
      returnedZipCode = coordinates.get("returnedZipCode").intValue();
    }
    else {
      idSale = null;
      latitude = null;
      longitude = null;
      returnedStreetName = null;
      returnedZipCode = null;
    }

    return new Sale(
        propertyId,
        number,
        borough,
        neighbourhood,
        buildingClassCategory,
        taxClassAtPresent,
        block,
        lot,
        easement,
        buildingClassAtPresent,
        address,
        apartmentNumber,
        zipCode,
        residentialUnits,
        commercialUnits,
        totalUnits,
        landSquareFeet,
        grossSquareFeet,
        yearBuilt,
        taxClassAtTimeOfSale,
        buildingClassAtTimeOfSale,
        salePrice,
        saleDate,
        idSale,
        latitude,
        longitude,
        returnedStreetName,
        returnedZipCode
    );
  }

  /**
   * Get all coordinate objects from the API.
   * @return List of all coordinates.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static List<Coordinates> getAllCoordinates() throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "allCoordinates {\n" +
            ID_SALE + "\n" +
            LATITUDE + "\n" +
            LONGITUDE + "\n" +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    Iterator<JsonNode> nodes = responseData.get("allCoordinates").elements();
    List<Coordinates> properties = new ArrayList<>();
    nodes.forEachRemaining(node -> {
      String id = node.get(ID_SALE).asText();
      Float latitude = node.get(LATITUDE).floatValue();
      Float longitude = node.get(LONGITUDE).floatValue();
      properties.add(new Coordinates(id, latitude, longitude));
    });
    return properties;
  }

  /**
   * Get a sale from the API by its ID.
   * @param id The ID of the sale property.
   * @return The sale property with all fields.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static Sale getById(String id) throws IOException{
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
    return getSaleFromNode(node);
  }

  /**
   * Get a sale from the API by the coordinates id.
   * @param id The ID of the Coordinate object.
   * @return The sale property with all fields.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static Sale getByCoordinatesId(String id) throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "propertyByCoordinatesId(id: \"" + id + "\") {\n" +
            PROPERTY_FIELDS +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    JsonNode node = responseData.get("propertyByCoordinatesId");
    return getSaleFromNode(node);
  }

  /**
   * Get a sale property by its ID. The property's only non-null fields are
   * grossSquareFeet and neighbourhood.
   * @param id The ID of the sale property.
   * @return The sale property.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static Sale getGrossSquareFeetAndNeighbourhoodById(String id) throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "propertyById(id: \"" + id + "\") {\n" +
            NEIGHBOURHOOD + "\n" +
            GROSS_SQUARE_FEET + "\n" +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    JsonNode node = responseData.get("propertyById");
    String neighbourhood = node.get(NEIGHBOURHOOD).asText();
    String grossSquareFeet = node.get(GROSS_SQUARE_FEET).asText();
    var sale = new Sale(id);
    sale.setNeighbourhood(neighbourhood);
    sale.setGrossSquareFeet(grossSquareFeet);
    return sale;
  }

  /**
   * Get all sale properties in the given neighbourhood.
   * @param neighbourhood The neighbourhood name.
   * @return The List of all sale properties in the neighbourhood.
   * @throws IOException Thrown if the communication with the API failed.
   */
  public static List<Sale> getAllByNeighbourhood(String neighbourhood) throws IOException{
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
    List<Sale> properties = new ArrayList<>();
    nodes.forEachRemaining(node -> properties.add(getSaleFromNode(node)));
    return properties;
  }
}
