package bertvangelder.db.artist;

import bertvangelder.db.DatabaseException;

/**
 * Created by Bert on 18/04/2017.
 */
public class ArtistRepositoryFactory {
    private static ArtistRepositoryFactory uniqueInstance;

    private ArtistRepositoryFactory() {
    }

    public static synchronized ArtistRepositoryFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ArtistRepositoryFactory();
        }
        return uniqueInstance;
    }

    public ArtistRepository createArtistRepository(String dbType) {
        ArtistRepository artistRepository;
        if(dbType.equals("sql")){
            artistRepository = ArtistSqlRepository.getInstance();
        } else {
            artistRepository = ArtistMemoryRepository.getInstance();
        }
        return artistRepository;
    }
}
