package service;

import jpaUtil.JpaUtil;
import model.Patient;
import repository.PatientRepository;

import javax.persistence.EntityManager;

public class PatientService {
    EntityManager em = JpaUtil.getEntityManager();
    PatientRepository patientRepository = new PatientRepository(em);

    public long getPatientIdByPassword(String username){
        long id =  patientRepository.getByUsername(username).getId();
        em.close();
        return id;
    }

    public Patient addPatient(Patient patient){
        patientRepository.save(patient);
        em.close();
        return patient;
    }

    public Patient updatePatient(Patient patient){
        patientRepository.update(patient);
        em.close();
        return patient;
    }

    public Patient getPatientById(long patientId){
        Patient patient = patientRepository.read(patientId);
        em.close();
        return patient;
    }
}
