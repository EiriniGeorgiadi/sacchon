package service;

import representation.CarbRepresentation;

import java.util.List;

public interface PatientCarbService {
    List<CarbRepresentation> getPatientCarbList(long patientId);
    CarbRepresentation getCarb(long patientId, long carbId);
    CarbRepresentation addPatientCarb(long patientId, CarbRepresentation carbRepresentation);
    CarbRepresentation editCarb(long patientId, CarbRepresentation carbRepresentation, long carbId);
    CarbRepresentation deleteCarb(long patientId, long carbId);
}
