package service;

import jpaUtil.JpaUtil;
import model.Carb;
import repository.CarbRepository;

import javax.persistence.EntityManager;

public class CarbService {
    EntityManager em = JpaUtil.getEntityManager();
    CarbRepository carbRepository = new CarbRepository(em);

    public Carb createCarb(Carb carb){
        carbRepository.save(carb);
        em.close();
        return carb;
    }

    public Carb updateCarb(Carb carb){
        carbRepository.update(carb);
        em.close();
        return carb;
    }

    public Carb getCarbById(long carbID){
        Carb carb = carbRepository.read(carbID);
        em.close();
        return carb;
    }

}
