package service.impl;

import dto.CarbDto;
import jpaUtil.JpaUtil;
import model.Carb;
import repository.CarbRepository;
import representation.CarbRepresentation;
import service.CarbService;

import javax.persistence.EntityManager;

public class CarbServiceImpl implements CarbService {

    public  CarbRepresentation createCarb(CarbRepresentation carbRepresentation){
        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);

        Carb carb = CarbDto.transferCarbRepresentationToCarb(carbRepresentation);
        carbRepository.save(carb);
        em.close();
        carbRepresentation = CarbDto.transferCarbToCarbRepresentation(carb);
        return carbRepresentation;
    }

    public CarbRepresentation updateCarb(CarbRepresentation carbRepresentation){
        Carb carb = CarbDto.transferCarbRepresentationToCarb(carbRepresentation);

        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);
        carbRepository.update(carb);
        em.close();

        carbRepresentation =CarbDto.transferCarbToCarbRepresentation(carb);
        return carbRepresentation;
    }

    public void deleteCarb(long carbID){
        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);
        carbRepository.delete(carbID);
        em.close();
    }

    public CarbRepresentation getCarbById(long carbID){
        EntityManager em = JpaUtil.getEntityManager();
        CarbRepository carbRepository = new CarbRepository(em);
        Carb carb = carbRepository.read(carbID);
        em.close();
        if (carb == null) return null;
        else return CarbDto.transferCarbToCarbRepresentation(carb);
    }

}
