package service;

import representation.CarbRepresentation;

public interface CarbService {
    CarbRepresentation createCarb(CarbRepresentation carbRepresentation);
    CarbRepresentation updateCarb(CarbRepresentation carbRepresentation);
    void deleteCarb(long carbID);
    CarbRepresentation getCarbById(long carbID);
}
