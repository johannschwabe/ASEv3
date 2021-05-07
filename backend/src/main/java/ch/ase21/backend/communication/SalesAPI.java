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
  private static final String NEIGHBORHOOD = "neighborhood";
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
      NEIGHBORHOOD + "\n" +
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

  private static Sale getSaleFromNode(JsonNode node){
    String propertyId = node.get(ID).asText();
    Integer number = node.get("number").intValue();
    Integer borough = node.get("borough").intValue();
    String neighbourhood = node.get(NEIGHBORHOOD).asText();
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
    String idSale = coordinates.get(ID_SALE).asText();
    Float latitude = coordinates.get(LATITUDE).floatValue();
    Float longitude = coordinates.get(LONGITUDE).floatValue();
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

  public static Sale getGrossSquareFeetAndNeighborhoodById(String id) throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "propertyById(id: \"" + id + "\") {\n" +
            NEIGHBORHOOD + "\n" +
            GROSS_SQUARE_FEET + "\n" +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    JsonNode node = responseData.get("propertyById");
    String neighborhood = node.get(NEIGHBORHOOD).asText();
    String grossSquareFeet = node.get(GROSS_SQUARE_FEET).asText();
    var sale = new Sale(id);
    sale.setNeighbourhood(neighborhood);
    sale.setGrossSquareFeet(grossSquareFeet);
    return sale;
  }

  public static List<Sale> getGrossSquareFeetAndSalePriceByNeighborhood(String neighborhood) throws IOException{
    HttpURLConnection connection = setupConnection(API_URL);
    String query =
        "{\n" +
          "propertiesByNeighborhood(neighborhood:\"" + neighborhood + "\") {\n" +
            ID + "\n" +
            GROSS_SQUARE_FEET + "\n" +
            SALE_PRICE + "\n" +
          "}\n" +
        "}";
    insertQuery(connection, query);
    JsonNode responseData = getResponseData(connection);
    Iterator<JsonNode> nodes = responseData.get("propertiesByNeighborhood").elements();
    List<Sale> sales = new ArrayList<>();
    nodes.forEachRemaining(node -> {
      String id = node.get(ID).asText();
      String grossSquareFeet = node.get(GROSS_SQUARE_FEET).asText();
      String salePrice = node.get(SALE_PRICE).asText();
      var sale = new Sale(id);
      sale.setGrossSquareFeet(grossSquareFeet);
      sale.setSalePrice(salePrice);
      sales.add(sale);
    });
    return sales;
  }
}
