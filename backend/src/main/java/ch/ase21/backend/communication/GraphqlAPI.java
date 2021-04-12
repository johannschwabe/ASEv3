package ch.ase21.backend.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class GraphqlAPI {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  protected static HttpURLConnection setupConnection(String urlString) throws IOException{
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/json; utf-8");
    connection.setRequestProperty("Accept", "application/json");
    return connection;
  }

  protected static void insertQuery(HttpURLConnection connection, String queryString) throws IOException{
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("query", queryString);
    String content = MAPPER.writeValueAsString(requestBody);
    OutputStream outputStream = connection.getOutputStream();
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    outputStreamWriter.write(content);
    outputStreamWriter.flush();
    outputStreamWriter.close();
    outputStream.close();
  }

  protected static JsonNode getResponseData(HttpURLConnection connection) throws IOException{
    connection.connect();
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();
    connection.disconnect();
    JsonNode responseObject = MAPPER.readValue(content.toString(), new TypeReference<>() {});
    return responseObject.get("data");
  }
}
