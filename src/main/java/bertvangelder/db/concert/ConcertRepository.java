package bertvangelder.db.concert;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Concert;

import java.util.ArrayList;

/**
 * Created by Bert on 18/04/2017.
 */
public interface ConcertRepository {
    Concert readConcert(long id) throws DatabaseException;
    ArrayList<Concert> readAllConcerts();
    void addConcert(Concert concert) throws DatabaseException;
    void updateConcert(Concert concert) throws DatabaseException;
    void deleteConcert(long id) throws DatabaseException;
    void finalize();
}
