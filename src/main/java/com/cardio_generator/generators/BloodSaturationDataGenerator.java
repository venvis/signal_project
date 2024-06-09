package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
import java.util.Random;

/**
 * The BloodSaturationDataGenerator class implements the PatientDataGenerator interface
 * to simulate blood oxygen saturation data for patients. This class generates blood oxygen
 * saturation values with minor variations to ensure they remain within a typical healthy range.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {

    private static final Random random = new Random();
    private int[] previousSaturationValues;

    /**
     * Constructs a BloodSaturationDataGenerator for the given number of patients.
     * Initializes each patient's saturation value to a starting value between 93 and 98.
     *
     * @param patientCount the number of patients for whom data is to be generated
     */
    public BloodSaturationDataGenerator(int patientCount) {
        previousSaturationValues = new int[patientCount + 1];

        // Initialize each patient's saturation value with a starting point between 93 and 98
        for (int i = 1; i <= patientCount; i++) {
            previousSaturationValues[i] = 93 + random.nextInt(6); // Initializes with a value between 93 and 98
        }
    }

    /**
     * Generates and outputs simulated blood saturation data for the specified patient.
     * The generated values will slightly vary to simulate realistic measurements.
     *
     * @param patientId the unique identifier of the patient
     * @param outputStrategy the strategy used to output the generated data
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate a small change in blood saturation value
            int fluctuation = random.nextInt(5) - 2; // -2, -1, 0, 1, or 2 to simulate small changes
            int newSaturationValue =
                previousSaturationValues[patientId] + fluctuation;

            // Ensure the saturation value stays within a healthy and realistic range
            if (newSaturationValue > 100) {
                newSaturationValue = 100;
            } else if (newSaturationValue < 90) {
                newSaturationValue = 90;
            }

            previousSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(
                patientId,
                System.currentTimeMillis(),
                "BloodSaturation",
                newSaturationValue + "%"
            );
        } catch (Exception e) {
            System.err.println(
                "Error generating blood saturation data for patient " +
                patientId
            );
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}

