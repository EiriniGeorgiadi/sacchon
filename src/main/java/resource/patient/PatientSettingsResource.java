package resource.patient;

import exception.AuthorizationException;
import org.restlet.Request;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import representation.PatientRepresentation;
import resource.ResourceUtils;
import security.Authentication;
import security.Shield;
import service.PatientService;
import service.impl.PatientServiceImpl;

public class PatientSettingsResource extends ServerResource {
    private long id;
    private PatientService patientService;

    protected void doInit() {
        patientService= new PatientServiceImpl();

        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);
        id = patientService.getPatientIdByUsername(authentication.getUsername());
    }


    @Get("json")
    public PatientRepresentation getPatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        return patientService.getPatient(id);
    }

    @Put("json")
    public PatientRepresentation updatePatient(PatientRepresentation patientRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
       return patientService.updatePatient(id, patientRepresentation);
    }



    @Delete("json")
    public void deletePatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        patientService.deletePatient(id);
    }

}
