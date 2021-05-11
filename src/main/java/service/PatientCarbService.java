package service;

import jpaUtil.JpaUtil;
import model.Carb;
import model.Patient;
import repository.CarbRepository;
import repository.PatientRepository;
import representation.CarbRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
        return carbRepresentationList;
    }

    public CarbRepresentation getCarb(long patientId,long carbId){
        CarbService carbService = new CarbService();
        Carb carb = carbService.getCarbById(carbId);
        if (!(carb.getPatient().getId() == patientId)){
            return null;
        }else return new CarbRepresentation(carb);
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

    public CarbRepresentation editCarb (long patientId,CarbRepresentation carbRepresentation,long carbId){
        if (carbRepresentation == null) return null;
        if (carbRepresentation.getId() !=0 && carbRepresentation.getId()!=carbId) return null;

        CarbService carbService =new CarbService();
        long expectedPatientID = carbService.getCarbById(carbId).getPatient().getId();
        if (!(expectedPatientID == patientId)) {
            return null;
        }

        carbRepresentation.setId(carbId);
        carbRepresentation.setPatientId(patientId);
        Carb carb = carbService.updateCarb(carbRepresentation.createCarb());
        return new CarbRepresentation(carb);
    }

    public boolean deleteCarb (long patientId,long carbId) {
        CarbService carbService = new CarbService();
        long expectedPatientID = carbService.getCarbById(carbId).getPatient().getId();
        if (!(expectedPatientID == patientId)) {
            return false;
        }
        carbService.deleteCarb(carbId);
        return true;
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
