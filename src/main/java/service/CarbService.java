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
        return carb;
    }

    public Carb updateCarb(Carb carb){
        carbRepository.update(carb);
        return carb;
    }

    public void deleteCarb(long carbID){
        carbRepository.delete(carbID);
    }

    public Carb getCarbById(long carbID){
        Carb carb = carbRepository.read(carbID);
        return carb;
    }

}
