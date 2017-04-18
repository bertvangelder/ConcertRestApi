package bertvangelder.db.venue;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Venue;
import bertvangelder.util.PersistenceManager;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bert on 13/04/2017.
 */
public class VenueSqlRepository implements VenueRepository {
    private static VenueSqlRepository uniqueInstance;
    private EntityManager em;

    private VenueSqlRepository() {
        em = PersistenceManager.INSTANCE.getEntityManager();
    }

    public static synchronized VenueSqlRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new VenueSqlRepository();
        }
        return uniqueInstance;
    }

    public Venue readVenue(long id) throws DatabaseException {
        Venue venue = em.find(Venue.class, id);
        if (venue == null) {
            throw new DatabaseException("Venue with this ID: " + id + " not found!");
        }
        return venue;
    }

    public ArrayList<Venue> readAllVenues() {
        TypedQuery<Venue> lQuery = em.createQuery("from Venue", Venue.class);
        List<Venue> venueList = lQuery.getResultList();
        return  new ArrayList<Venue>(venueList);
    }

    public void addVenue(Venue venue) throws DatabaseException {
        Venue v = em.find(Venue.class, venue.getVenueId());
        if (v == null) {
            em.getTransaction().begin();
            em.persist(venue);
            em.getTransaction().commit();
        } else {
            throw new DatabaseException("Unable to create. Venue with id: " + venue.getVenueId() + "already exists!");
        }
    }

    public void updateVenue(Venue venue) throws DatabaseException {
        Venue v = em.find(Venue.class, venue.getVenueId());
        if (v == null) {
            throw new DatabaseException("Unable to update. Venue with id:" + venue.getVenueId() + "does not exist!");
        }
        em.clear();
        v.setName(venue.getName());
        v.setLocation(venue.getLocation());
        em.getTransaction().begin();
        em.merge(v);
        em.getTransaction().commit();
    }

    public void deleteVenue(long id) throws DatabaseException {
        Venue venue = em.find(Venue.class, id);
        em.getTransaction().begin();
        if (venue != null) {
            em.remove(venue);
        } else {
            throw new DatabaseException("Unable to delete. Venue with id: " + id + " not found!");
        }
        em.getTransaction().commit();
    }

    @Override
    public void finalize() {
        em.close();
        PersistenceManager.INSTANCE.close();
    }
}