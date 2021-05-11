package resource.patient;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Carb;
import org.restlet.Request;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.CarbRepository;
import repository.PatientRepository;
import representation.CarbRepresentation;
import resource.ResourceUtils;
import security.Authentication;
import security.Shield;
import service.PatientCarbService;
import service.PatientService;

import javax.persistence.EntityManager;
import java.util.List;

public class PatientCarbResource extends ServerResource {
    private long patientId;
    private long carbId;

    protected void doInit() {
        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);

        PatientService patientService= new  PatientService();
        patientId = patientService.getPatientIdByPassword(authentication.getUsername());
        carbId = Long.parseLong(getAttribute("carbId"));
    }


    @Get("json")
    public CarbRepresentation getCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientCarbService patientCarbService = new PatientCarbService();
        return patientCarbService.getCarb(patientId,carbId);
    }

    @Put("json")
    public CarbRepresentation updateCarb(CarbRepresentation carbRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientCarbService patientCarbService = new PatientCarbService();
        return patientCarbService.editCarb(patientId,carbRepresentation,carbId);
//        if (carbRepresentation == null) return null;
//
//        carbRepresentation.setId(carbId);
//        carbRepresentation.setPatientId(patientId);
//        EntityManager em = JpaUtil.getEntityManager();
//        CarbRepository carbRepository = new CarbRepository(em);
//        Carb carb = carbRepresentation.createCarb();
//        em.detach(carb);
//        carb.setId(carbId);
//        carbRepository.update(carb);
//        return carbRepresentation;
    }

    @Delete("json")
    public boolean deleteCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientCarbService patientCarbService = new PatientCarbService();
        return patientCarbService.deleteCarb(patientId,carbId);
//        EntityManager em = JpaUtil.getEntityManager();
//        CarbRepository carbRepository = new CarbRepository(em);
//        carbRepository.delete(carbRepository.read(carbId).getId());
    }
}