package bertvangelder.db.artist;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Artist;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Bert on 18/04/2017.
 */
public class ArtistMemoryRepository implements ArtistRepository {
    private ArrayList<Artist> artists;
    private volatile static ArtistMemoryRepository uniqueInstance;
    private static long counter = 0;

    private ArtistMemoryRepository() {
        artists = new ArrayList<Artist>();
    }

    public static synchronized ArtistMemoryRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ArtistMemoryRepository();
        }
        return uniqueInstance;
    }
    @Override
    public Artist readArtist(long id) throws DatabaseException {
        Artist artist = new Artist();
        artist.setArtistId(id);
        boolean bestaat = false;
        for (Artist a : artists) {
            if (a.getArtistId() == artist.getArtistId()) {
                artist.setName(a.getName());
                bestaat = true;
            }
        }
        if (!bestaat) {
            throw new DatabaseException("Artist with this ID: " + id + " not found!");
        }
        return artist;
    }

    @Override
    public ArrayList<Artist> readAllArtists() {
        return artists;
    }

    @Override
    public void addArtist(Artist artist) throws DatabaseException {
        boolean exists = false;
        for (Artist a : artists) {
            if (artist.equals(a)) {
                exists = true;
            }
        }
        if (!exists) {
            artist.setArtistId(counter);
            counter++;
            artists.add(artist);
        } else {
            throw new DatabaseException("Unable to create. Artist with id: " + artist.getArtistId() + "already exists!");
        }
    }

    @Override
    public void updateArtist(Artist artist) throws DatabaseException {
        boolean exists = false;
        for (Artist a : artists) {
            if (a.getArtistId() == artist.getArtistId()) {
                a.setName(artist.getName());
                exists = true;
            }
        }
        if (!exists) {
            throw new DatabaseException("Unable to update. Artist with id:" + artist.getArtistId() + "does not exist!");
        }
    }

    @Override
    public void deleteArtist(long id) throws DatabaseException {
        Iterator it = artists.iterator();
        boolean gevonden = false;
        Artist a;
        while (it.hasNext() && !gevonden) {
            a = (Artist) it.next();
            if (a.getArtistId() == id) {
                it.remove();
                gevonden = true;
            }
        }
        if (!gevonden) {
            throw new DatabaseException("Unable to delete. Artist with id: " + id + " not found!");
        }
    }

    @Override
    public void finalize() {
    }
}
