package com.assignment;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CSVGenerator {

    public void writeToCSV(String filePath, List<Map<String, String>> rows, List<String> headers) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(headers.toArray(new String[0]));

            for (Map<String, String> row : rows) {
                String[] csvRow = headers.stream().map(row::get).toArray(String[]::new);
                writer.writeNext(csvRow);
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }
}
