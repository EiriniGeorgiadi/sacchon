package service;

import dto.CarbDto;
import jpaUtil.JpaUtil;
import model.Carb;
import repository.CarbRepository;
import representation.CarbRepresentation;

import javax.persistence.EntityManager;

public class CarbService {

    public static CarbRepresentation createCarb(CarbRepresentation carbRepresentation){
        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);

        Carb carb = CarbDto.transferCarbRepresentationToCarb(carbRepresentation);
        carbRepository.save(carb);
        em.close();
        carbRepresentation = CarbDto.transferCarbToCarbRepresentation(carb);
        return carbRepresentation;
    }

    public static CarbRepresentation updateCarb(CarbRepresentation carbRepresentation){
        Carb carb = CarbDto.transferCarbRepresentationToCarb(carbRepresentation);

        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);
        carbRepository.update(carb);
        em.close();

        carbRepresentation =CarbDto.transferCarbToCarbRepresentation(carb);
        return carbRepresentation;
    }

    public static void deleteCarb(long carbID){
        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);
        carbRepository.delete(carbID);
        em.close();
    }

    public static Carb getCarbById(long carbID){
        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);
        Carb carb = carbRepository.read(carbID);
        em.close();
        return carb;
    }

}
