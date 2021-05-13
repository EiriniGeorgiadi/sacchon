package representation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRepresentation {
    private long id;
    private String username;
    private String password;
    private String name;
    private String address;
    private String email;
    private int age;
    private String sex;
    private long doctorId;
    private Date dateRegistered;
    private boolean consultationChanged;
    private String role = "patient";
    private Date recentConsultation;
    private Date recentCarb;
    private Date recentGlucose;

    private String uri;
}
