package com.data_management;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code DataReader1} class implements the {@code DataReader} interface
 * and is responsible for reading patient data from a file and storing it
 * in the {@link DataStorage} system.
 */
public class DataReader1 implements DataReader {

    private File aFile;

    /**
     * Constructs a {@code DataReader1} instance with the specified file path.
     * The file at the specified path is used to read patient data.
     *
     * @param pathStr the path to the data file
     */
    public DataReader1(String pathStr) {
        this.aFile = new File(pathStr);
        System.out.println("DataReader created!!");
    }

    /**
     * Reads data from the file and stores it in the provided {@code DataStorage} instance.
     * The data is read line by line, parsed, and then added to the data storage.
     *
     * @param dataStorage the data storage system to store the patient data
     * @throws IOException if an I/O error occurs during reading
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (Scanner fileReader = new Scanner(aFile)) {
            while (fileReader.hasNextLine()) {
                String[] lineIter = fileReader.nextLine().split(",");
                int patientId = Integer.parseInt(getString(0, lineIter));
                long timestamp = Long.parseLong(getString(1, lineIter));
                String label = getString(2, lineIter);
                double measurementValue = Double.parseDouble(
                    getString(3, lineIter).split("%")[0]
                );

                // Adding the parsed data to the data storage
                dataStorage.addPatientData(
                    patientId,
                    measurementValue,
                    label,
                    timestamp
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("File Read error");
        }
    }

    /**
     * Extracts the string from the specified position in the given array.
     * The data is assumed to be in the format "key: value" and this method
     * extracts the value.
     *
     * @param pos the position of the string to extract
     * @param toExtractFrom the array to extract the string from
     * @return the extracted string value
     */
    private String getString(int pos, String[] toExtractFrom) {
        return toExtractFrom[pos].split(": ")[1];
    }
}
