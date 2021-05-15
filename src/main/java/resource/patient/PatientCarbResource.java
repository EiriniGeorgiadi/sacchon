package resource.patient;

import exception.AuthorizationException;
import org.restlet.Request;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import representation.CarbRepresentation;
import resource.ResourceUtils;
import security.Authentication;
import security.Shield;
import service.PatientCarbService;
import service.impl.PatientCarbServiceImpl;
import service.PatientService;
import service.impl.PatientServiceImpl;

public class PatientCarbResource extends ServerResource {
    private long patientId;
    private long carbId;
    private PatientService patientService;
    private PatientCarbService patientCarbService;


    protected void doInit() {
        patientService= new PatientServiceImpl();
        patientCarbService = new PatientCarbServiceImpl();

        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);
        patientId = patientService.getPatientIdByUsername(authentication.getUsername());
        carbId = Long.parseLong(getAttribute("carbId"));
    }


    @Get("json")
    public CarbRepresentation getCarb(){
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            return patientCarbService.getCarb(patientId,carbId);
        } catch (AuthorizationException e) {
            throw new AuthorizationException();
        }
    }

    @Put("json")
    public CarbRepresentation updateCarb(CarbRepresentation carbRepresentation) {
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            return patientCarbService.editCarb(patientId,carbRepresentation,carbId);
        } catch (AuthorizationException e) {
            throw new AuthorizationException();
        }
    }

    @Delete("json")
    public CarbRepresentation deleteCarb() throws AuthorizationException {
        try {
            ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
            return patientCarbService.deleteCarb(patientId,carbId);
        } catch (AuthorizationException e) {
            throw new AuthorizationException();
        }

    }
}