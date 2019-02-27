package store.wine.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	static EntityManagerFactory emf = null;
	private static final String WINE_STORE = "wine_store";

    public static EntityManager getEntityManager() {
        if (emf == null) {
            try {
                emf =  Persistence.createEntityManagerFactory(WINE_STORE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emf.createEntityManager();
    }
    
    @Override
    protected void finalize() throws Throwable {
    	if (emf != null) {
            try {
                emf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    	super.finalize();
    }
    
 
}
