package resource.patient;

import exception.AuthorizationException;
import org.restlet.Request;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
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

        PatientService patientService= new  PatientService();
        patientId = patientService.getPatientIdByPassword(authentication.getUsername());
    }


    @Get("json")
    public List<CarbRepresentation> getCarbList() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientCarbService patientCarbService = new PatientCarbService();
        return patientCarbService.getPatientCarbList(patientId);
    }

    @Post("json")
    public CarbRepresentation addCarb(CarbRepresentation carbRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientCarbService patientCarbService = new PatientCarbService();
        return patientCarbService.addPatientCarb(patientId,carbRepresentationIn);
    }

}

