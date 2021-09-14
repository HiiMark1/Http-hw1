package ru.kpfu.itis.amirhan;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Http implements HttpClient {

      @Override
      public String get(String url, Map<String, String> headers, Map<String, String> params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                  URL getUrl = new URL(url);
                  HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
                  connection.setRequestProperty("Content-Type", "application/json");
                  connection.setRequestMethod("GET");

                  for (int i = 0; i < headers.size(); i++) {
                        String header =
                        connection.setRequestProperty(headers, headers.get(header));
                  }

                  try (BufferedReader reader = new BufferedReader(
                          new InputStreamReader(connection.getInputStream())
                  )) {
                        String input;
                        while ((input = reader.readLine()) != null) {
                              stringBuilder.append(input);
                        }
                  }

                  connection.disconnect();

            } catch (IOException e) {
                  e.printStackTrace();
            }
            return stringBuilder.toString();
      }

      @Override
      public String post(String url, Map<String, String> headers, Map<String, String> params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                  URL postUrl = new URL(url);
                  HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();

                  postConnection.setRequestMethod("POST");
                  postConnection.setRequestProperty("Content-Type", "application/json");
                  postConnection.setRequestProperty("Accept", "application/json");
                  postConnection.setDoOutput(true);

                  String jsonInputString = "{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\", \"email\":\"tenali.ramakrishna1@gmail.com\", \"status\":\"active\"}";

                  try (OutputStream outputStream = postConnection.getOutputStream()) {
                        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                        outputStream.write(input, 0, input.length);
                  }

                  try (BufferedReader reader =
                               new BufferedReader(
                                       new InputStreamReader(postConnection.getInputStream(), StandardCharsets.UTF_8)
                               )
                  ) {
                        String input;
                        while ((input = reader.readLine()) != null) {
                              stringBuilder.append(input.trim());
                        }
                  }

                  postConnection.disconnect();
            } catch (IOException e) {
                  e.printStackTrace();
            }
            return stringBuilder.toString();
      }

      public static void main(String[] args) {
            Http httpClient = new Http();

            System.out.println(httpClient.get("https://postman-echo.com/get", null, null));
            System.out.println(httpClient.post("https://postman-echo.com/post", null, null));
      }
}
