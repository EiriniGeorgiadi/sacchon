package resource.patient;

import exception.AuthorizationException;
import org.restlet.Request;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import representation.CarbRepresentation;
import resource.ResourceUtils;
import security.Authentication;
import security.Shield;
import service.PatientCarbService;
import service.PatientService;

import java.util.List;

public class PatientCarbListResource extends ServerResource {
    private long patientId;

    protected void doInit() {
        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);
        patientId = PatientService.getPatientIdByUsername(authentication.getUsername());
    }

    @Get("json")
    public List<CarbRepresentation> getCarbList(){
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            return PatientCarbService.getPatientCarbList(patientId);
        } catch (AuthorizationException e) {
            throw new AuthorizationException();
        }
    }

    @Post("json")
    public CarbRepresentation addCarb(CarbRepresentation carbRepresentationIn)  {
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            return PatientCarbService.addPatientCarb(patientId, carbRepresentationIn);
        } catch (AuthorizationException e) {
            throw new AuthorizationException();
        }
    }

}

