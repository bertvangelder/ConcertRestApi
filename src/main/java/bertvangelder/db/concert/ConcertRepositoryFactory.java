package bertvangelder.db.concert;

import bertvangelder.db.DatabaseException;

/**
 * Created by Bert on 18/04/2017.
 */
public class ConcertRepositoryFactory {
    private static ConcertRepositoryFactory uniqueInstance;

    private ConcertRepositoryFactory() {
    }

    public static synchronized ConcertRepositoryFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ConcertRepositoryFactory();
        }
        return uniqueInstance;
    }

    public ConcertRepository createConcertRepository(String dbType) {
        ConcertRepository concertRepository;
        if(dbType.equals("sql")){
            concertRepository = ConcertSqlRepository.getInstance();
        } else {
            concertRepository = ConcertMemoryRepository.getInstance();
        }
        return concertRepository;
    }
}
