package bertvangelder.db.concert;

import bertvangelder.db.DatabaseException;
import bertvangelder.db.venue.VenueMemoryRepository;
import bertvangelder.db.artist.ArtistMemoryRepository;
import bertvangelder.model.Artist;
import bertvangelder.model.Concert;
import bertvangelder.model.Venue;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Bert on 18/04/2017.
 */
public class ConcertMemoryRepository implements ConcertRepository {
    private ArrayList<Concert> concerts;
    private volatile static ConcertMemoryRepository uniqueInstance;
    private static long counter = 0;

    private ConcertMemoryRepository() {
        concerts = new ArrayList<Concert>();
    }

    public static synchronized ConcertMemoryRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ConcertMemoryRepository();
        }
        return uniqueInstance;
    }

    @Override
    public Concert readConcert(long id) throws DatabaseException {
        Concert concert = new Concert();
        concert.setConcertId(id);
        boolean exists = false;
        for (Concert concert1 : concerts) {
            if (concert1.getConcertId() == concert.getConcertId()) {
                concert.setName(concert1.getName());
                concert.setVenue(new Venue(concert1.getVenue()));
                concert.setArtist(new Artist(concert1.getArtist()));
                concert.setTime(concert1.getTime());
                concert.setDate(concert1.getDate());
                exists = true;
            }
        }
        if (!exists) {
            throw new DatabaseException("Concert with this ID: " + id + " not found!");
        }
        return concert;
    }

    @Override
    public ArrayList<Concert> readAllConcerts() {
        return concerts;
    }

    @Override
    public void addConcert(Concert concert) throws DatabaseException {
        boolean exists = false;
        for (Concert concert1 : concerts) {
            if (concert.equals(concert1)) {
                exists = true;
            }
        }
        if (!exists) {
            addArtistAndVenueIfNotExist(concert);
            concert.setConcertId(counter);
            counter++;
            concerts.add(concert);
        } else {
            throw new DatabaseException("Unable to create. Concert with id: " + concert.getConcertId() + "already exists!");
        }
    }

    private void addArtistAndVenueIfNotExist(Concert concert){
        try{
            ArtistMemoryRepository artistMemoryRepository = ArtistMemoryRepository.getInstance();
            artistMemoryRepository.addArtist(concert.getArtist());
        }catch(DatabaseException ex){
            ex.getMessage();
        }
        try{
            VenueMemoryRepository venueMemoryRepository = VenueMemoryRepository.getInstance();
            venueMemoryRepository.addVenue(concert.getVenue());
        }catch (DatabaseException ex){
            ex.getMessage();
        }
    }

    @Override
    public void updateConcert(Concert concert) throws DatabaseException {
        boolean exists = false;
        for (Concert concert1 : concerts) {
            if (concert1.getConcertId() == concert.getConcertId()) {
                concert.setName(concert1.getName());
                addArtistAndVenueIfNotExist(concert);
                concert.setVenue(new Venue(concert1.getVenue()));
                concert.setArtist(new Artist(concert1.getArtist()));
                concert.setTime(concert1.getTime());
                concert.setDate(concert1.getDate());
                exists = true;
            }
        }
        if (!exists) {
            throw new DatabaseException("Unable to update. Concert with id:" + concert.getConcertId() + "does not exist!");
        }
    }

    @Override
    public void deleteConcert(long id) throws DatabaseException {
        Iterator it = concerts.iterator();
        boolean gevonden = false;
        Concert c;
        while (it.hasNext() && !gevonden) {
            c = (Concert) it.next();
            if (c.getConcertId() == id) {
                it.remove();
                gevonden = true;
            }
        }
        if (!gevonden) {
            throw new DatabaseException("Unable to delete. Concert with id: " + id + " not found!");
        }
    }

    @Override
    public void finalize() {

    }
}
