package rules;

import representation.CarbRepresentation;
import service.CarbService;

public class CarbRules {
    public static boolean carbExists(Long carbID){
        if (CarbService.getCarbById(carbID)==null) return false;
        else return true;
    }

    public static boolean patientCanAccessCarb(CarbRepresentation carbRepresentation,long patientID){
        if (carbRepresentation.getPatientId()==patientID) return true;
        else return false;
    }
}
