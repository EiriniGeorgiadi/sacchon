package service.impl;

import dto.PatientDto;
import jpaUtil.JpaUtil;
import model.Patient;
import repository.PatientRepository;
import representation.PatientRepresentation;
import service.PatientService;

import javax.persistence.EntityManager;

public class PatientServiceImpl implements PatientService {



    public long getPatientIdByUsername(String username){
        EntityManager em = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(em);
        long id =  patientRepository.getByUsername(username).getId();
        em.close();
        return id;
    }


    public PatientRepresentation getPatient(long patientID){
        EntityManager em = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(em);
        Patient patient = patientRepository.read(patientID);
        PatientRepresentation patientRepresentation = PatientDto.transferPatientToPatientRepresentation(patient);
        em.close();
        return patientRepresentation;
    }

    public void deletePatient(long patientID){
        EntityManager em = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(em);
        patientRepository.delete(patientID);
        em.close();
    }

    public PatientRepresentation updatePatient(long patientID, PatientRepresentation patientRepresentation){
        if (patientRepresentation == null) return null;
        EntityManager em = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(em);

        //get the stored patient from the database
        PatientRepresentation storedPatientRep = getPatient(patientID);

        patientRepresentation.setId(patientID);
        patientRepresentation.setDateRegistered(storedPatientRep.getDateRegistered());
        if (patientRepresentation.getRecentCarb()==null) patientRepresentation.setRecentCarb(storedPatientRep.getRecentCarb());
        if (patientRepresentation.getRecentGlucose()==null) patientRepresentation.setRecentGlucose(storedPatientRep.getRecentGlucose());
        if (patientRepresentation.getRecentConsultation()==null)patientRepresentation.setRecentConsultation(storedPatientRep.getRecentConsultation());
        Patient patient = PatientDto.transferPatientRepresentationToPatient(patientRepresentation);
        patientRepository.update(patient);
        em.close();
        return PatientDto.transferPatientToPatientRepresentation(patient);
    }

}
