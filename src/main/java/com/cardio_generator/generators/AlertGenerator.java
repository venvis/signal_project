package com.alerts;

import com.DataEvaluaters.BloodOxygenSaturationevaluater;
import com.DataEvaluaters.BloodPressureEvaluater;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;

/**
 * The {@code AlertGenerator} class is designed to monitor patient data
 * and generate alerts when specific predefined conditions are met.
 * This class utilizes a {@link DataStorage} instance to access patient data
 * and evaluate it against defined health criteria.
 */
public class AlertGenerator {

    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with the specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data for monitoring and evaluation.
     *
     * @param dataStorage the data storage system providing access to patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions are met.
     * If a condition is met, an alert is triggered using the {@link #triggerAlert} method.
     * This method defines the specific conditions under which an alert will be triggered.
     *
     * @param patient the patient whose data will be evaluated for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Current time for evaluation
        Long currTime = System.currentTimeMillis(); // getting the current system time

        // Retrieve patient records from 10 minutes ago till current time
        ArrayList<PatientRecord> records = patient.getRecords(
            currTime - 600000,
            currTime
        ); // 600000 ms = 10 minutes

        // Evaluate diastolic blood pressure
        Alert Dbp = BloodPressureEvaluater.evaluateBP(
            records,
            "DiastollicPressure"
        );
        boolean DbpTriggered = false;

        // Evaluate systolic blood pressure
        Alert Sbp = BloodPressureEvaluater.evaluateBP(
            records,
            "SystollicPressure"
        );
        boolean SbpTriggered = false;

        // Evaluate blood oxygen saturation
        Alert BOs = BloodOxygenSaturationevaluater.evaluateBO(records);
        boolean BOsTriggered = false;

        // Check and trigger alerts for diastolic blood pressure
        if (Dbp != null && !DbpTriggered) {
            triggerAlert(Dbp);
            DbpTriggered = true;
        }

        // Check and trigger alerts for systolic blood pressure
        if (Sbp != null && !SbpTriggered) {
            if (BOs != null && !BOsTriggered) {
                // Trigger Hypotensive Hypoxemia alert
                triggerAlert(
                    new Alert(
                        patient.getPatientId(),
                        "Hypotensive Hypoxemia Alert",
                        currTime
                    )
                );
                BOsTriggered = true;
            }
            triggerAlert(Sbp);
            SbpTriggered = true;
        }

        // Check and trigger alerts for blood oxygen saturation
        if (BOs != null && !BOsTriggered) {
            triggerAlert(BOs);
            BOsTriggered = true;
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended
     * to notify medical staff, log the alert, or perform other actions.
     * The method assumes that the alert information is fully formed when passed as an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        String alertString =
            "PatientID: " +
            alert.getPatientId() +
            " Timestamp: " +
            alert.getTimestamp() +
            " Label: " +
            alert.getCondition();
        System.out.println(alertString);
    }
}
