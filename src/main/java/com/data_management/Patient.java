package com.data_management;

import java.util.ArrayList;
import java.util.List;

public class Patient {

    private int patientId;
    private List<PatientRecord> patientRecords;

    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    public void addRecord(
        double measurementValue,
        String recordType,
        long timestamp
    ) {
        PatientRecord record = new PatientRecord(
            this.patientId,
            measurementValue,
            recordType,
            timestamp
        );
        this.patientRecords.add(record);
    }
}
