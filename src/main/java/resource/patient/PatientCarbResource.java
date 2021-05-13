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
import service.PatientService;

public class PatientCarbResource extends ServerResource {
    private long patientId;
    private long carbId;

    protected void doInit() {
        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);

        patientId = PatientService.getPatientIdByUsername(authentication.getUsername());
        carbId = Long.parseLong(getAttribute("carbId"));
    }


    @Get("json")
    public CarbRepresentation getCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        return PatientCarbService.getCarb(patientId,carbId);
    }

    @Put("json")
    public CarbRepresentation updateCarb(CarbRepresentation carbRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        return PatientCarbService.editCarb(patientId,carbRepresentation,carbId);
    }

    @Delete("json")
    public boolean deleteCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        return PatientCarbService.deleteCarb(patientId,carbId);
    }
}