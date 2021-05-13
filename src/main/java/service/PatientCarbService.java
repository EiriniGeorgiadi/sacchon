package service;

import dto.CarbDto;
import jpaUtil.JpaUtil;
import model.Carb;
import repository.PatientRepository;
import representation.CarbRepresentation;
import representation.PatientRepresentation;
import rules.CarbRules;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientCarbService {

    public static List<CarbRepresentation> getPatientCarbList(long patientId) {
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

    public static CarbRepresentation getCarb(long patientId, long carbId) {
        if (!CarbRules.carbExists(carbId)) return null;

        CarbRepresentation carbRepresentation = CarbService.getCarbById(carbId);
        if (!CarbRules.patientCanAccessCarb(carbRepresentation,patientId)) {
            return null;
        } else return carbRepresentation;
    }

    public static CarbRepresentation addPatientCarb(long patientId, CarbRepresentation carbRepresentation) {
        if (carbRepresentation == null) return null;

        //add a carb in the database
        carbRepresentation = addCarb(carbRepresentation, patientId);

        // update the Last Carb in Patient table
        PatientRepresentation patientRepresentation = PatientService.getPatient(patientId);
        updatePatientResendCarb(carbRepresentation, patientRepresentation);

        return carbRepresentation;
    }

    public static CarbRepresentation editCarb(long patientId, CarbRepresentation carbRepresentation, long carbId) {
        if (carbRepresentation == null) return null;
        if (!CarbRules.carbExists(carbId)) return null;

        carbRepresentation.setId(carbId);
        carbRepresentation.setPatientId(CarbService.getCarbById(carbId).getPatientId());
        if (!CarbRules.patientCanAccessCarb(carbRepresentation,patientId)) return null;

        carbRepresentation = CarbService.updateCarb(carbRepresentation);
        return carbRepresentation;
    }

    public static CarbRepresentation deleteCarb(long patientId, long carbId) {
        if (!CarbRules.carbExists(carbId)) return null;
        CarbRepresentation carbRepresentation = CarbService.getCarbById(carbId);
        if (!CarbRules.patientCanAccessCarb(carbRepresentation,patientId)) return null;
        CarbService.deleteCarb(carbId);
        return carbRepresentation;
    }

    private static CarbRepresentation addCarb(CarbRepresentation carbRepresentation, long patientId) {
        carbRepresentation.setPatientId(patientId);
        return CarbService.createCarb(carbRepresentation);
    }


    private static void updatePatientResendCarb(CarbRepresentation carbRepresentation, PatientRepresentation patientRepresentation) {
        patientRepresentation.setRecentCarb(Date.from(ZonedDateTime.now().toInstant()));
        PatientService.updatePatient(patientRepresentation.getId(), patientRepresentation);
    }
}
