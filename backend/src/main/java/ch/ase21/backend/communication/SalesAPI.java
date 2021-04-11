package ch.ase21.backend.communication;

import ch.ase21.backend.entity.Property;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SalesAPI {

  private static HttpURLConnection setupConnection() throws IOException{
    URL url = new URL("salesapi:8080");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/json; utf-8");
    connection.setRequestProperty("Accept", "application/json");
    return connection;
  }

  private static void writeToBody(HttpURLConnection connection, String string) throws IOException{
    OutputStream outputStream = connection.getOutputStream();
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    outputStreamWriter.write(string);
    outputStreamWriter.flush();
    outputStreamWriter.close();
    outputStream.close();
  }

  private static String getResponse(HttpURLConnection connection) throws IOException{
    connection.connect();
    BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();
    connection.disconnect();
    return content.toString();
  }

  public static List<Property> getAllProperties() throws IOException{
    HttpURLConnection connection = setupConnection();
    writeToBody(connection,
        "{\n" +
              "allProperties {\n" +
                "id\n" +
                "coordinates {\n" +
                  "latitude\n" +
                  "longitude\n" +
                "}\n" +
              "}\n" +
            "}"
    );
    String response = getResponse(connection);
    ObjectMapper mapper = new ObjectMapper();
    List<JsonNode> nodes = mapper.readValue(response, new TypeReference<>() {});
    List<Property> properties = new ArrayList<>();
    nodes.forEach(node -> {
      String id = node.get("id").asText();
      JsonNode coordinates = node.get("coordinates");
      Float latitude = coordinates.get("latitude").floatValue();
      Float longitude = coordinates.get("longitude").floatValue();
      properties.add(new Property(id, latitude, longitude));
    });
    return properties;
  }
}
