package service.impl;

import dto.CarbDto;
import jpaUtil.JpaUtil;
import model.Carb;
import repository.PatientRepository;
import representation.CarbRepresentation;
import representation.PatientRepresentation;
import rules.CarbRules;
import service.CarbService;
import service.PatientCarbService;
import service.PatientService;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientCarbServiceImpl implements PatientCarbService {
    private PatientService patientService;

    public List<CarbRepresentation> getPatientCarbList(long patientId) {
        EntityManager em = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(em);
        List<Carb> carbList = patientRepository.getCarbList(patientId);
        List<CarbRepresentation> carbRepresentationList = new ArrayList<>();

        for (Carb c : carbList) {
            carbRepresentationList.add(CarbDto.transferCarbToCarbRepresentation(c));
        }
        em.close();
        return carbRepresentationList;
    }

    public CarbRepresentation getCarb(long patientId, long carbId) {
        if (!CarbRules.carbExists(carbId)) return null;

        CarbService carbServiceImpl = new CarbServiceImpl();
        CarbRepresentation carbRepresentation = carbServiceImpl.getCarbById(carbId);
        if (!CarbRules.patientCanAccessCarb(carbRepresentation,patientId)) {
            return null;
        } else return carbRepresentation;
    }

    public CarbRepresentation addPatientCarb(long patientId, CarbRepresentation carbRepresentation) {
        if (carbRepresentation == null) return null;

        //add a carb in the database
        carbRepresentation = addCarb(carbRepresentation, patientId);

        // update the Last Carb in Patient table
        patientService= new PatientServiceImpl();
        PatientRepresentation patientRepresentation = patientService.getPatient(patientId);
        updatePatientResendCarb(carbRepresentation, patientRepresentation);

        return carbRepresentation;
    }

    public CarbRepresentation editCarb(long patientId, CarbRepresentation carbRepresentation, long carbId) {
        if (carbRepresentation == null) return null;
        if (!CarbRules.carbExists(carbId)) return null;

        carbRepresentation.setId(carbId);
        CarbService carbServiceImpl = new CarbServiceImpl();
        carbRepresentation.setPatientId(carbServiceImpl.getCarbById(carbId).getPatientId());
        if (!CarbRules.patientCanAccessCarb(carbRepresentation,patientId)) return null;

        carbRepresentation = carbServiceImpl.updateCarb(carbRepresentation);
        return carbRepresentation;
    }

    public  CarbRepresentation deleteCarb(long patientId, long carbId) {
        if (!CarbRules.carbExists(carbId)) return null;

        CarbService carbServiceImpl = new CarbServiceImpl();
        CarbRepresentation carbRepresentation = carbServiceImpl.getCarbById(carbId);
        if (!CarbRules.patientCanAccessCarb(carbRepresentation,patientId)) return null;
        carbServiceImpl.deleteCarb(carbId);
        return carbRepresentation;
    }

    private CarbRepresentation addCarb(CarbRepresentation carbRepresentation, long patientId) {
        carbRepresentation.setPatientId(patientId);
        CarbService carbServiceImpl = new CarbServiceImpl();
        return carbServiceImpl.createCarb(carbRepresentation);
    }


    private void updatePatientResendCarb(CarbRepresentation carbRepresentation, PatientRepresentation patientRepresentation) {
        patientRepresentation.setRecentCarb(Date.from(ZonedDateTime.now().toInstant()));
        patientService= new PatientServiceImpl();
        patientService.updatePatient(patientRepresentation.getId(), patientRepresentation);
    }
}
