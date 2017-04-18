package bertvangelder.db.artist;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Artist;
import bertvangelder.util.PersistenceManager;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bert on 13/04/2017.
 */
public class ArtistSqlRepository implements ArtistRepository {
    private static ArtistSqlRepository uniqueInstance;
    private EntityManager em;

    private ArtistSqlRepository() {
        em = PersistenceManager.INSTANCE.getEntityManager();
    }

    public static synchronized ArtistSqlRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ArtistSqlRepository();
        }
        return uniqueInstance;
    }

    @Override
    public Artist readArtist(long id) throws DatabaseException {
        Artist artist = em.find(Artist.class, id);
        if (artist == null) {
            throw new DatabaseException("Artist with id: " + id + " not found!");
        }
        return artist;
    }

    @Override
    public ArrayList<Artist> readAllArtists() {
        TypedQuery<Artist> lQuery = em.createQuery("from Artist", Artist.class);
        List<Artist> artistList = lQuery.getResultList();
        return new ArrayList<Artist>(artistList);
    }

    @Override
    public void addArtist(Artist artist) throws DatabaseException {
        Artist a = em.find(Artist.class, artist.getArtistId());
        if (a == null) {
            em.getTransaction().begin();
            em.persist(artist);
            em.getTransaction().commit();
        } else {
            throw new DatabaseException("Unable to create. Artist with id: " + artist.getArtistId() + "already exists!");
        }
    }

    @Override
    public void updateArtist(Artist artist) throws DatabaseException {
        Artist a = em.find(Artist.class, artist.getArtistId());
        if (a == null) {
            throw new DatabaseException("Unable to update. Artist with id:" + artist.getArtistId() + "does not exist!");
        }
        em.clear();
        a.setName(artist.getName());
        em.getTransaction().begin();
        em.merge(a);
        em.getTransaction().commit();
    }

    @Override
    public void deleteArtist(long id) throws DatabaseException {
        Artist artist = em.find(Artist.class, id);
        em.getTransaction().begin();
        if (artist != null) {
            em.remove(artist);
        } else {
            throw new DatabaseException("Unable to delete. Artist with id: " + id + " not found!");
        }
        em.getTransaction().commit();
    }

    @Override
    public void finalize() {
        em.close();
        PersistenceManager.INSTANCE.close();
    }
}