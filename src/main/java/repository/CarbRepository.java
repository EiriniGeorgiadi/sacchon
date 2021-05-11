package repository;

import model.Carb;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarbRepository extends Repository<Carb, Long>{
    public CarbRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Carb> getEntityClass() {
        return Carb.class;
    }

    @Override
    public String getClassName() {
        return Carb.class.getName();
    }


}
