package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader {
    private String outputDir;

    public FileDataReader(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Invalid directory: " + outputDir);
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt")); // Assuming data files are .txt files
        if (files == null) {
            throw new IOException("Failed to list files in directory: " + outputDir);
        }

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 4) {
                        throw new IOException("Invalid data format in file: " + file.getName());
                    }

                    int patientId = Integer.parseInt(parts[0]);
                    double measurementValue = Double.parseDouble(parts[1]);
                    String recordType = parts[2];
                    long timestamp = Long.parseLong(parts[3]);

                    dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
                }
            }
        }
    }
}
s
