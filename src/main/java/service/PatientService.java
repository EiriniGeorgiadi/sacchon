package service;

import jpaUtil.JpaUtil;
import model.Patient;
import repository.PatientRepository;
import representation.PatientRepresentation;

import javax.persistence.EntityManager;

public class PatientService {
    EntityManager em = JpaUtil.getEntityManager();
    PatientRepository patientRepository = new PatientRepository(em);


    public long getPatientIdByPassword(String username){
        long id =  patientRepository.getByUsername(username).getId();
        return id;
    }

    public Patient addPatient(Patient patient){
        PatientRepository patientRepository = new PatientRepository(em);
        patientRepository.save(patient);
        return patient;
    }

    public PatientRepresentation getPatient(long patientID){
        Patient patient = patientRepository.read(patientID);
        PatientRepresentation patientRepresentation = new PatientRepresentation(patient);
        return patientRepresentation;
    }

    public void deletePatient(long patientID){
        patientRepository.delete(patientID);
    }

    public PatientRepresentation updatePatient (long patientID, PatientRepresentation patientRepresentation){
        if (patientRepresentation == null) return null;

        Patient patient = patientRepresentation.createPatient();

        //get the stored patient from the database
        Patient storedPatient = getPatientById(patientID);

        patient.setId(patientID);
        patient.setDateRegistered(storedPatient.getDateRegistered());
        patientRepository.update(patient);
        return new PatientRepresentation(patient);
    }

    public Patient updatePatient(Patient patient){
        patientRepository.update(patient);
        return patient;
    }

    public Patient getPatientById(long patientId){
        Patient patient = patientRepository.read(patientId);
        return patient;
    }
}
