package resource.patient;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import org.restlet.Request;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import repository.PatientRepository;
import resource.ResourceUtils;
import security.Authentication;
import security.Shield;
import service.PatientService;

import javax.persistence.EntityManager;
import java.util.Date;

public class PatientCarbAverageResource extends ServerResource {
    private long patientId;

    protected void doInit() {

        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);

        patientId = PatientService.getPatientIdByUsername(authentication.getUsername());
    }

    @Get
    public Double getAverageCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        String start = getQueryValue("start");
        String end = getQueryValue("end");
        Date dateStart = ResourceUtils.stringToDate(start, -1);
        Date dateEnd = ResourceUtils.stringToDate(end, 1);

        EntityManager em = JpaUtil.getEntityManager();

        PatientRepository patientRepository = new PatientRepository(em);
        Double carb = patientRepository.getCarbAverage(this.patientId, dateStart, dateEnd);

        em.close();
        return carb;
    }
}
