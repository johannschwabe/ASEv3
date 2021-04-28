package ch.ase21.backend.communication;

import ch.ase21.backend.entity.Coordinates;
import ch.ase21.backend.entity.Sale;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.*;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.util.*;

public class SalesAPI extends GraphqlAPI{

  private static final String propertyFields =
      "id\n" +
      "number\n" +
      "borough\n" +
      "neighborhood\n" +
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
      "grossSquareFeet\n" +
      "yearBuilt\n" +
      "taxClassAtTimeOfSale\n" +
      "buildingClassAtTimeOfSale\n" +
      "salePrice\n" +
      "saleDate\n" +
      "coordinates{\n" +
        "idSale\n" +
        "latitude\n" +
        "longitude\n" +
        "returnedStreetName\n" +
        "returnedZipCode\n" +
      "}\n";


  private SalesAPI() {/* void */}

  private static Sale getSaleFromNode(JsonNode node){
    String propertyId = node.get("id").asText();
    Integer number = node.get("number").intValue();
    Integer borough = node.get("borough").intValue();
    String neighbourhood = node.get("neighborhood").asText();
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
    String grossSquareFeet = node.get("grossSquareFeet").asText();
    Integer yearBuilt = node.get("yearBuilt").intValue();
    Integer taxClassAtTimeOfSale = node.get("taxClassAtTimeOfSale").intValue();
    String buildingClassAtTimeOfSale = node.get("buildingClassAtTimeOfSale").asText();
    String salePrice = node.get("salePrice").asText();
    Timestamp saleDate = Timestamp.valueOf(node.get("saleDate").asText());
    // Coordinates
    JsonNode coordinates = node.get("coordinates");
    String idSale = coordinates.get("idSale").asText();
    Float latitude = coordinates.get("latitude").floatValue();
    Float longitude = coordinates.get("longitude").floatValue();
    String returnedStreetName = coordinates.get("returnedStreetName").asText();
    Integer returnedZipCode = coordinates.get("returnedZipCode").intValue();

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

  public static List<Coordinates> getAllCoordinates() throws IOException{
    HttpURLConnection connection = setupConnection("http://salesapi:8080/graphql");
    String query =
        "{\n" +
          "allCoordinates {\n" +
            "idSale\n" +
            "latitude\n" +
            "longitude\n" +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    Iterator<JsonNode> nodes = responseData.get("allCoordinates").elements();
    List<Coordinates> properties = new ArrayList<>();
    nodes.forEachRemaining(node -> {
      String id = node.get("idSale").asText();
      Float latitude = node.get("latitude").floatValue();
      Float longitude = node.get("longitude").floatValue();
      properties.add(new Coordinates(id, latitude, longitude));
    });
    return properties;
  }

  public static Sale getById(String id) throws IOException{
    HttpURLConnection connection = setupConnection("http://salesapi:8080/graphql");
    String query =
        "{\n" +
          "propertyById(id: \"" + id + "\") {\n" +
            propertyFields +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    JsonNode node = responseData.get("propertyById");
    return getSaleFromNode(node);
  }

  public static Sale getByCoordinatesId(String id) throws IOException{
    HttpURLConnection connection = setupConnection("http://salesapi:8080/graphql");
    String query =
        "{\n" +
          "propertyByCoordinatesId(id: \"" + id + "\") {\n" +
            propertyFields +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    JsonNode node = responseData.get("propertyByCoordinatesId");
    return getSaleFromNode(node);
  }
}
