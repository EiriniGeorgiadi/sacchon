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
import service.impl.PatientServiceImpl;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class PatientCarbDailyAverageResource extends ServerResource {
    private long patientId;
    private PatientService patientService;

    protected void doInit() {
        patientService= new PatientServiceImpl();

        Request req = Request.getCurrent();
        Authentication authentication = new Authentication(req);
        patientId = patientService.getPatientIdByUsername(authentication.getUsername());
    }

    @Get
    public List<Double> getAverageCarb() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        String start = getQueryValue("start");
        String end = getQueryValue("end");
        Date dateStart = ResourceUtils.stringToDate(start, -1);
        Date dateEnd = ResourceUtils.stringToDate(end, 1);

        EntityManager em = JpaUtil.getEntityManager();

        PatientRepository patientRepository = new PatientRepository(em);
        List<Double> carbList = patientRepository.getCarbAverageList(this.patientId, dateStart, dateEnd);

        em.close();
        return carbList;
    }
}
