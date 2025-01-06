package com.assignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

    private final String inputFolder;

    public FileProcessor(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public List<File> getValidFiles() {
        List<File> validFiles = new ArrayList<>();
        File folder = new File(inputFolder);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid input folder: " + inputFolder);
            return validFiles;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (isValidFormat(file.getName())) {
                    validFiles.add(file);
                }
            }
        }

        return validFiles;
    }

    private boolean isValidFormat(String fileName) {
        return fileName.endsWith(".pdf") || fileName.endsWith(".doc") ||
                fileName.endsWith(".docx") || fileName.endsWith(".txt");
    }
}
