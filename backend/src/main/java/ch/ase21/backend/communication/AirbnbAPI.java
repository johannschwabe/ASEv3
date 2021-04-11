package ch.ase21.backend.communication;

import ch.ase21.backend.entity.Property;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AirbnbAPI {

  private HttpURLConnection setupConnection() throws IOException{
    URL url = new URL("airbnbapi:8080");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/json; utf-8");
    connection.setRequestProperty("Accept", "application/json");
    return connection;
  }

  private void writeToBody(HttpURLConnection connection, String string) throws IOException{
    OutputStream outputStream = connection.getOutputStream();
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    outputStreamWriter.write(string);
    outputStreamWriter.flush();
    outputStreamWriter.close();
    outputStream.close();
  }

  private String getResponse(HttpURLConnection connection) throws IOException{
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

  public List<Property> getAllProperties() throws IOException{
    HttpURLConnection connection = setupConnection();
    writeToBody(connection,
        "{\n" +
            "allProperties {\n" +
            "id\n" +
            "latitude\n" +
            "longitude\n" +
            "}\n" +
            "}"
    );
    String response = getResponse(connection);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(response, new TypeReference<>() {});
  }
}
