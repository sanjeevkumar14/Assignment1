package com.assignment;

import java.util.HashMap;
import java.util.Map;

public class DataMapper {

    private final Map<String, String> fieldMappings;

    public DataMapper() {
        fieldMappings = new HashMap<>();
        initializeDefaultMappings();
    }

    public void addMapping(String extractedField, String csvColumn) {
        fieldMappings.put(extractedField, csvColumn);
    }

    public String getMappedColumn(String extractedField) {
        return fieldMappings.get(extractedField);
    }

    public Map<String, String> mapToCsvRow(Map<String, String> parsedData) {
        Map<String, String> csvRow = new HashMap<>();

        for (Map.Entry<String, String> entry : parsedData.entrySet()) {
            String csvColumn = getMappedColumn(entry.getKey());
            if (csvColumn != null) {
                csvRow.put(csvColumn, entry.getValue());
            }
        }

        return csvRow;
    }

    private void initializeDefaultMappings() {
        fieldMappings.put("name", "Name");
        fieldMappings.put("email", "Email");
        fieldMappings.put("contactNumber", "Contact Number");
        fieldMappings.put("skills", "Skills");
        fieldMappings.put("education", "Education");
    }
}
