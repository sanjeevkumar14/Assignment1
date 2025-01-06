package com.assignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // Define input and output paths
        String inputFolder = "C:\\Users\\sanje\\Desktop\\Assign";
        String outputCSV = "C:\\Users\\sanje\\Desktop\\Out\\output.csv";

        // Define Rchilli API configurations
        String apiKey = "your-api-key";
        String resumeEndpoint = "https://api.rchilli.com/resume";
        String jdEndpoint = "https://api.rchilli.com/jd";

        // Initialize components
        FileProcessor fileProcessor = new FileProcessor(inputFolder);
        RchilliAPIClient apiClient = new RchilliAPIClient(apiKey, resumeEndpoint, jdEndpoint);
        DataMapper dataMapper = new DataMapper();
        CSVGenerator csvGenerator = new CSVGenerator();

        // Fetch files from the input folder
        List<File> files = fileProcessor.getValidFiles();
        if (files.isEmpty()) {
            System.out.println("No valid files found in the input folder.");
            return;
        }

        List<Map<String, String>> csvRows = new ArrayList<>();
        for (File file : files) {
            try {
                boolean isResume = file.getName().toLowerCase().contains("resume");
                System.out.println("Processing: " + file.getName());

                // Parse file using the API
                Map<String, String> parsedData = apiClient.parseFile(file.getAbsolutePath(), isResume);
                if (parsedData == null || parsedData.isEmpty()) {
                    System.err.println("Error parsing file: " + file.getName());
                    continue;
                }

                // Map parsed data to CSV format
                Map<String, String> csvRow = dataMapper.mapToCsvRow(parsedData);
                csvRows.add(csvRow);

            } catch (Exception e) {
                System.err.println("Error processing file: " + file.getName() + ". " + e.getMessage());
            }
        }

        if (csvRows.isEmpty()) {
            System.out.println("No valid data to write to the CSV.");
            return;
        }

        // Define CSV headers
        List<String> headers = List.of("Name", "Email", "Contact Number", "Skills", "Education");
        try {
            // Write data to CSV
            csvGenerator.writeToCSV(outputCSV, csvRows, headers);
            System.out.println("Processing complete. CSV generated at: " + outputCSV);
        } catch (Exception e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}
