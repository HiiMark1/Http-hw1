package ru.kpfu.itis.amirhan;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Http implements HttpClient {

      @Override
      public String get(String url, Map<String, String> headers, Map<String, String> params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                  URL getUrl = new URL(url);
                  HttpURLConnection getConnection = (HttpURLConnection) getUrl.openConnection();
                  getConnection.setRequestMethod("GET");
                  getConnection.setConnectTimeout(15000);
                  getConnection.setReadTimeout(5000);

                  for (String header : headers.keySet()) {
                        getConnection.setRequestProperty(header, headers.get(header));
                  }

                  try (BufferedReader reader = new BufferedReader(
                          new InputStreamReader(getConnection.getInputStream())
                  )) {
                        String input;
                        while ((input = reader.readLine()) != null) {
                              stringBuilder.append(input);
                        }
                  }

                  getConnection.disconnect();

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
                  postConnection.setDoOutput(true);

                  String jsonInputString = "{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\", \"email\":\"tenali.ramakrishna1@gmail.com\", \"status\":\"active\"}";

                  for (String header : headers.keySet()) {
                        postConnection.setRequestProperty(header, headers.get(header));
                  }

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

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Accept", "application/json");

            Map<String, String> params = new HashMap<>();
            params.put("Name", "LLrk");
            params.put("Version","5.0");
            params.put("Who","You");

            System.out.println(httpClient.get("https://postman-echo.com/get", headers, null));
            System.out.println(httpClient.get("https://postman-echo.com/post", null, params));
      }
}
