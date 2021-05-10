package service;

import jpaUtil.JpaUtil;
import model.Carb;
import model.Patient;
import repository.CarbRepository;
import repository.PatientRepository;
import representation.CarbRepresentation;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientCarbService {
    EntityManager em = JpaUtil.getEntityManager();
    PatientRepository patientRepository = new PatientRepository(em);


    public List<CarbRepresentation> getPatientCarbList(long patientId){
        List<Carb> carbList = patientRepository.getCarbList(patientId);
        List<CarbRepresentation> carbRepresentationList = new ArrayList<>();

        for (Carb c : carbList) {
            carbRepresentationList.add(new CarbRepresentation(c));
        }
        em.close();
        return carbRepresentationList;
    }

    public CarbRepresentation addPatientCarb(long patientId,CarbRepresentation carbRepresentation){
        if (carbRepresentation == null) return null;

        //add a carb in the database
        Carb carb = addCarb(carbRepresentation, patientId);

        // update the Last Carb in Patient table
        PatientService patientService = new PatientService();
        Patient patient = patientService.getPatientById(patientId);
        updatePatientResendCarb(carb,patient);

        return new CarbRepresentation(carb);
    }

    private Carb addCarb(CarbRepresentation carbRepresentation,long patientId){
        carbRepresentation.setPatientId(patientId);
        Carb carb = carbRepresentation.createCarb();
        CarbService carbService = new CarbService();
        return carbService.createCarb(carb);
    }



    private void updatePatientResendCarb(Carb carb, Patient patient){
        patient.setRecentCarb(carb.getDate());
        PatientService patientService = new PatientService();
        patientService.updatePatient(patient);
    }
}
