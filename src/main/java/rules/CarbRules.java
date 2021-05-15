package rules;

import representation.CarbRepresentation;
import service.CarbService;
import service.impl.CarbServiceImpl;

public class CarbRules {

    public static boolean carbExists(Long carbID){
        CarbService carbService = new CarbServiceImpl();
        if (carbService.getCarbById(carbID)==null) return false;
        else return true;
    }

    public static boolean patientCanAccessCarb(CarbRepresentation carbRepresentation,long patientID){
        if (carbRepresentation.getPatientId()==patientID) return true;
        else return false;
    }
}
