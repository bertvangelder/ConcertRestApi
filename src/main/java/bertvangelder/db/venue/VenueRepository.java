package bertvangelder.db.venue;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Venue;

import java.util.ArrayList;

/**
 * Created by Bert on 18/04/2017.
 */
public interface VenueRepository {
    Venue readVenue(long id) throws DatabaseException;
    ArrayList<Venue> readAllVenues();
    void addVenue(Venue venue) throws DatabaseException;
    void updateVenue(Venue venue) throws DatabaseException;
    void deleteVenue(long id) throws DatabaseException;
    void finalize();
}
