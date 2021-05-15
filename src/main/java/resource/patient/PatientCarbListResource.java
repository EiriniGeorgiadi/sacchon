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
import service.impl.PatientCarbServiceImpl;
import service.PatientService;
import service.impl.PatientServiceImpl;

import java.util.List;

public class PatientCarbListResource extends ServerResource {
    private long patientId;
    private PatientService patientService;
    private PatientCarbService patientCarbService;

    protected void doInit() {
        patientService= new PatientServiceImpl();
        patientCarbService = new PatientCarbServiceImpl();

        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);
        patientId = patientService.getPatientIdByUsername(authentication.getUsername());
    }

    @Get("json")
    public List<CarbRepresentation> getCarbList(){
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            return patientCarbService.getPatientCarbList(patientId);
        } catch (AuthorizationException e) {
            throw new AuthorizationException();
        }
    }

    @Post("json")
    public CarbRepresentation addCarb(CarbRepresentation carbRepresentationIn)  {
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            return patientCarbService.addPatientCarb(patientId, carbRepresentationIn);
        } catch (AuthorizationException e) {
            throw new AuthorizationException();
        }
    }

}

