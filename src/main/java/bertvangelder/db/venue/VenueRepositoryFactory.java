package bertvangelder.db.venue;

import bertvangelder.db.DatabaseException;

/**
 * Created by Bert on 18/04/2017.
 */
public class VenueRepositoryFactory {
    private static VenueRepositoryFactory uniqueInstance;

    private VenueRepositoryFactory() {
    }

    public static synchronized VenueRepositoryFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new VenueRepositoryFactory();
        }
        return uniqueInstance;
    }

    public VenueRepository createVenueRepository(String dbType)  {
        VenueRepository venueRepository;
        if(dbType.equals("sql")){
            venueRepository = VenueSqlRepository.getInstance();
        } else {
            venueRepository = VenueMemoryRepository.getInstance();
        }
        return venueRepository;
    }
}