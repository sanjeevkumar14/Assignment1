package com.assignment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RchilliAPIClient {

    private final String apiKey;
    private final String resumeEndpoint;
    private final String jdEndpoint;
    private final Gson gson;

    public RchilliAPIClient(String apiKey, String resumeEndpoint, String jdEndpoint) {
        this.apiKey = apiKey;
        this.resumeEndpoint = resumeEndpoint;
        this.jdEndpoint = jdEndpoint;
        this.gson = new Gson(); // Initialize Gson for JSON parsing
    }

    public Map<String, String> parseFile(String filePath, boolean isResume) {
        String endpoint = isResume ? resumeEndpoint : jdEndpoint;

        try {
            HttpClient client = HttpClient.newHttpClient();

            // Build HTTP Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "multipart/form-data")
                    .header("API-Key", apiKey)
                    .POST(HttpRequest.BodyPublishers.ofFile(Path.of(filePath)))
                    .build();

            // Send Request and Receive Response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("API Error. HTTP Status: " + response.statusCode());
                System.err.println("Response Body: " + response.body());
                return new HashMap<>();
            }

            // Parse JSON Response
            return parseResponse(response.body());
        } catch (Exception e) {
            System.err.println("Error parsing file: " + filePath);
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private Map<String, String> parseResponse(String responseBody) {
        try {
            // Convert JSON to Map using Gson
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            return gson.fromJson(responseBody, mapType);
        } catch (Exception e) {
            System.err.println("Error parsing API response: " + e.getMessage());
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
