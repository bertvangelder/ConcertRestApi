package bertvangelder.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum PersistenceManager {
    INSTANCE;
    private EntityManagerFactory emFactory;

    PersistenceManager(){
        emFactory= Persistence.createEntityManagerFactory("concertpu");
    }
    public EntityManager getEntityManager(){
        return emFactory.createEntityManager();
    }
    public void close(){
        emFactory.close();
    }
}