package bertvangelder.db.concert;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Artist;
import bertvangelder.model.Concert;
import bertvangelder.model.Venue;
import bertvangelder.util.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bert on 13/04/2017.
 */
public class ConcertSqlRepository implements ConcertRepository {
    private static ConcertSqlRepository uniqueInstance;
    private EntityManager em;

    private ConcertSqlRepository() {
        em = PersistenceManager.INSTANCE.getEntityManager();
    }

    public static synchronized ConcertSqlRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ConcertSqlRepository();
        }
        return uniqueInstance;
    }

    public Concert readConcert(long id) throws DatabaseException {
        Concert concert = em.find(Concert.class, id);
        if (concert == null) {
            throw new DatabaseException("Concert with id: " + id + " not found!");
        }
        return concert;
    }

    public ArrayList<Concert> readAllConcerts() {
        TypedQuery<Concert> lQuery = em.createQuery("from Concert ", Concert.class);
        List<Concert> concertList = lQuery.getResultList();
        return new ArrayList<Concert>(concertList);
    }

    public void addConcert(Concert concert) throws DatabaseException {
        Concert c = em.find(Concert.class, concert.getConcertId());
        if (c == null) {
            //in case we want to add a concert with a new artist and/or venue
            addArtistIfNotExists(concert.getArtist());
            addVenueIfNotExists(concert.getVenue());

            em.getTransaction().begin();
            em.persist(concert);
            em.getTransaction().commit();
        } else {
            throw new DatabaseException("Unable to create. Concert with id: " + concert.getConcertId() + "already exists!");
        }
    }

    private void addArtistIfNotExists(Artist artist) {
        Artist a = em.find(Artist.class, artist.getArtistId());
        if (a == null) {
            em.getTransaction().begin();
            em.persist(artist);
            em.getTransaction().commit();
        }
    }

    private void addVenueIfNotExists(Venue venue) {
        Venue v = em.find(Venue.class, venue.getVenueId());
        if (v == null) {
            em.getTransaction().begin();
            em.persist(venue);
            em.getTransaction().commit();
        }
    }

    public void updateConcert(Concert concert) throws DatabaseException {
        Concert c = em.find(Concert.class, concert.getConcertId());
        if (c == null) {
            throw new DatabaseException("Unable to update. Concert with id:" + concert.getConcertId() + "does not exist!");
        }
        em.clear();
        c.setName(concert.getName());
        //in case we want to update a concert with a new artist
        addArtistIfNotExists(concert.getArtist());
        c.setArtist(concert.getArtist());

        //in case we want to update a concert with a new venue
        addVenueIfNotExists(concert.getVenue());
        c.setVenue(concert.getVenue());

        c.setDate(concert.getDate());
        c.setTime(concert.getTime());
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
    }

    public void deleteConcert(long id) throws DatabaseException {
        Concert concert = em.find(Concert.class, id);
        em.getTransaction().begin();
        if (concert != null) {
            em.remove(concert);
        } else {
            throw new DatabaseException("Unable to delete. Concert with id: " + id + " not found!");
        }
        em.getTransaction().commit();
    }

    @Override
    public void finalize() {
        em.close();
        PersistenceManager.INSTANCE.close();
    }
}