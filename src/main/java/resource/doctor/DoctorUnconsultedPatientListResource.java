package resource.doctor;

import dto.PatientDto;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Patient;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import repository.DoctorRepository;
import representation.PatientRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class DoctorUnconsultedPatientListResource extends ServerResource {
    private long doctorId;

    protected void doInit() {
        doctorId = Long.parseLong(getAttribute("doctorId"));
    }

    @Get("json")
    public List<PatientRepresentation> getPatientList() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        EntityManager em = JpaUtil.getEntityManager();
        DoctorRepository doctorRepository = new DoctorRepository(em);
        List<Patient> patientList = doctorRepository.getUnconsultedPatientList();

        List<PatientRepresentation> patientRepresentationList = new ArrayList<>();

        for (Patient patient : patientList) {
            patientRepresentationList.add(PatientDto.transferPatientToPatientRepresentation(patient));
        }

        em.close();

        return patientRepresentationList;
    }
}
