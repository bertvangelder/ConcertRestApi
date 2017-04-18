package bertvangelder.db.artist;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Artist;

import java.util.ArrayList;

/**
 * Created by Bert on 18/04/2017.
 */
public interface ArtistRepository {
    Artist readArtist(long id) throws DatabaseException;
    ArrayList<Artist> readAllArtists();
    void addArtist(Artist artist) throws DatabaseException;
    void updateArtist(Artist artist) throws DatabaseException;
    void deleteArtist(long id) throws DatabaseException;
    void finalize() ;
}
