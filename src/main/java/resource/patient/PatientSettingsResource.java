package resource.patient;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Patient;
import org.restlet.Request;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.PatientRepository;
import representation.PatientRepresentation;
import resource.ResourceUtils;
import security.Authentication;
import security.Shield;
import service.PatientService;

import javax.persistence.EntityManager;

public class PatientSettingsResource extends ServerResource {
    private long id;

    protected void doInit() {

        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);

        PatientService patientService= new  PatientService();
        id = patientService.getPatientIdByPassword(authentication.getUsername());
    }


    @Get("json")
    public PatientRepresentation getPatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientService patientService = new PatientService();
        return patientService.getPatient(id);
//
//        EntityManager em = JpaUtil.getEntityManager();
//        PatientRepository patientRepository = new PatientRepository(em);
//        Patient patient = patientRepository.read(id);
//        PatientRepresentation patientRepresentation = new PatientRepresentation(patient);
//        em.close();
//        return patientRepresentation;
    }

    @Put("json")
    public PatientRepresentation updatePatient(PatientRepresentation patientRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientService patientService = new PatientService();
       return patientService.updatePatient(id, patientRepresentation);
    }



    @Delete("json")
    public void deletePatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        PatientService patientService = new PatientService();
        patientService.deletePatient(id);
//        EntityManager em = JpaUtil.getEntityManager();
//        PatientRepository patientRepository = new PatientRepository(em);
//        patientRepository.delete(patientRepository.read(id).getId());
    }

}
