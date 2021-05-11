package dto;

import model.Doctor;
import model.Patient;
import representation.PatientRepresentation;

public class PatientDto {
    public static PatientRepresentation transferPatientToPatientRepresentation(Patient patient){
        PatientRepresentation patientRepresentation = new PatientRepresentation();

        patientRepresentation.setId(patient.getId());
        patientRepresentation.setUsername(patient.getUsername());
        patientRepresentation.setPassword(patient.getPassword());
        patientRepresentation.setName(patient.getName());
        patientRepresentation.setAddress(patient.getAddress());
        patientRepresentation.setEmail(patient.getEmail());
        patientRepresentation.setAge(patient.getAge());
        patientRepresentation.setSex(patient.getSex());
        patientRepresentation.setDateRegistered(patient.getDateRegistered());
        if (patient.getDoctor() != null) {
            patientRepresentation.setDoctorId(patient.getDoctor().getId());
        }
        patientRepresentation.setConsultationChanged(patient.isConsultationChanged());
        patientRepresentation.setUri("http://localhost:9000/v1/patient/" + patient.getId());
        return patientRepresentation;
    }

    public static Patient transferPatientRepresentationToPatient(PatientRepresentation patientRepresentation){
        Patient patient = new Patient();
        patient.setId(patientRepresentation.getId());
        patient.setUsername(patientRepresentation.getUsername());
        patient.setPassword(patientRepresentation.getPassword());
        patient.setName(patientRepresentation.getName());
        patient.setAddress(patientRepresentation.getAddress());
        patient.setEmail(patientRepresentation.getEmail());
        patient.setAge(patientRepresentation.getAge());
        patient.setSex(patientRepresentation.getSex());
        patient.setDateRegistered(patientRepresentation.getDateRegistered());
        if(patientRepresentation.getDoctorId()!=0){
            Doctor doctor = new Doctor();
            doctor.setId(patientRepresentation.getDoctorId());
            patient.setDoctor(doctor);
        }
        return patient;
    }
}
