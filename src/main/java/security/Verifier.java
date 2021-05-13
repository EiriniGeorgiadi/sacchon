package security;

import jpaUtil.JpaUtil;
import model.ChiefDoctor;
import model.Doctor;
import model.Patient;
import model.User;
import org.restlet.Request;
import org.restlet.security.Role;
import org.restlet.security.SecretVerifier;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;

import javax.persistence.EntityManager;

public class Verifier extends SecretVerifier {

    public int verify(String username, char[] password) {
        EntityManager em = JpaUtil.getEntityManager();
        int result =SecretVerifier.RESULT_INVALID;
        //check db for user
        PatientRepository patientRepository = new PatientRepository(em);
        Patient patient = patientRepository.getByUsername(username);
        if (checkRole(patient,username,password,"patient")) result= SecretVerifier.RESULT_VALID;

        DoctorRepository doctorRepository = new DoctorRepository(em);
        Doctor doctor = doctorRepository.getByUsername(username);
        if (checkRole(doctor,username,password,"doctor")) result= SecretVerifier.RESULT_VALID;

        ChiefDoctorRepository chiefDoctorRepository = new ChiefDoctorRepository(em);
        ChiefDoctor chiefDoctor = chiefDoctorRepository.getByUsername(username);
        if (checkRole(chiefDoctor,username,password,"chiefDoctor")) result = SecretVerifier.RESULT_VALID;

        em.close();
        return result;
    }

    private boolean checkRole(User user, String username, char[] password, String role){
        if (verifyUser(user,username,password)) {
            setRole(role);
            return true;
        }
        return false;
    }

    private boolean verifyUser(User user, String username, char[] password){
        if (user == null) return false;
        String passwordInDb = user.getPassword();
        return (compare(passwordInDb.toCharArray(), password) && user.getUsername().equals(username)) ?  true :  false;

    }

    private void setRole(String role){
        Request request = Request.getCurrent();
        request.getClientInfo().getRoles().add
                    (new Role(role));
    }
}
