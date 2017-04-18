package bertvangelder.db.venue;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Venue;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Bert on 18/04/2017.
 */
public class VenueMemoryRepository implements VenueRepository {
    private ArrayList<Venue> venues;
    private volatile static VenueMemoryRepository uniqueInstance;
    private static long counter = 0;

    private VenueMemoryRepository() {
        venues = new ArrayList<Venue>();
    }

    public static synchronized VenueMemoryRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new VenueMemoryRepository();
        }
        return uniqueInstance;
    }
    @Override
    public Venue readVenue(long id) throws DatabaseException {
        Venue venue = new Venue();
        venue.setVenueId(id);
        boolean exists = false;
        for (Venue v : venues) {
            if (v.getVenueId() == venue.getVenueId()) {
                venue.setName(v.getName());
                venue.setLocation(v.getLocation());
                exists = true;
            }
        }
        if (!exists) {
            throw new DatabaseException("Venue with this ID: " + id + " not found!");
        }
        return venue;
    }


    @Override
    public ArrayList<Venue> readAllVenues() {
        return venues;
    }

    @Override
    public void addVenue(Venue venue) throws DatabaseException {
        boolean exists = false;
        for (Venue v : venues) {
            if (venue.equals(v)) {
                exists = true;
            }
        }
        if (!exists) {
            venue.setVenueId(counter);
            counter++;
            venues.add(venue);
        } else {
            throw new DatabaseException("Unable to create. Venue with id: " + venue.getVenueId() + "already exists!");
        }
    }

    @Override
    public void updateVenue(Venue venue) throws DatabaseException {
        boolean exists = false;
        for (Venue v : venues) {
            if (v.getVenueId() == venue.getVenueId()) {
                v.setName(venue.getName());
                v.setLocation(venue.getLocation());
                exists = true;
            }
        }
        if (!exists) {
            throw new DatabaseException("Unable to update. Venue with id:" + venue.getVenueId() + "does not exist!");
        }
    }

    @Override
    public void deleteVenue(long id) throws DatabaseException {
        Iterator it = venues.iterator();
        boolean gevonden = false;
        Venue v;
        while (it.hasNext() && !gevonden) {
            v = (Venue) it.next();
            if (v.getVenueId() == id) {
                it.remove();
                gevonden = true;
            }
        }
        if (!gevonden) {
            throw new DatabaseException("Unable to delete. Venue with id: " + id + " not found!");
        }
    }

    @Override
    public void finalize() {
    }
}
