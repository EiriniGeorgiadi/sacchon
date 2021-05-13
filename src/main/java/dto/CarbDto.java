package dto;

import Utils.Utils;
import model.Carb;
import model.Patient;
import representation.CarbRepresentation;

import java.time.ZonedDateTime;
import java.util.Date;

public class CarbDto {
    public static CarbRepresentation transferCarbToCarbRepresentation(Carb carb){
        CarbRepresentation carbRepresentation = new CarbRepresentation();
        carbRepresentation.setId(carb.getId());
        carbRepresentation.setCarb(carb.getCarb());
        carbRepresentation.setDate(carb.getDate());
        if (carb.getPatient()!= null )carbRepresentation.setPatientId(carb.getPatient().getId());
        carbRepresentation.setUri("http://localhost:9000/v1/carb/" + carb.getId());
        return carbRepresentation;
    }

    public static Carb transferCarbRepresentationToCarb(CarbRepresentation carbRepresentation){
        Carb carb = new Carb();
        carb.setId(carbRepresentation.getId());
        carb.setCarb(carbRepresentation.getCarb());
        if (carbRepresentation.getDate() == null) {
            carb.setDate(Date.from(ZonedDateTime.now().minusDays(carbRepresentation.getDateOffsetDays()).toInstant()));
        } else {
            carb.setDate(carbRepresentation.getDate());
        }
        carb.setSimpleDate(Utils.getSimpleDate(carb.getDate()));
        if(carbRepresentation.getPatientId()!=0){
            Patient patient = new Patient();
            patient.setId(carbRepresentation.getPatientId());
            carb.setPatient(patient);
        }
        return carb;
    }
}
