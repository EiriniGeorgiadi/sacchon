package service;

import representation.PatientRepresentation;

public interface PatientService {
    long getPatientIdByUsername(String username);
    PatientRepresentation getPatient(long patientID);
    void deletePatient(long patientID);
    PatientRepresentation updatePatient (long patientID, PatientRepresentation patientRepresentation);

}
