package representation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CarbRepresentation {
    private long id;
    private double carb;
    private Date date;
    private long dateOffsetDays;
    private long patientId;

    private String uri;

}