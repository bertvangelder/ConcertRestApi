package bertvangelder.service;

import bertvangelder.db.DatabaseException;
import bertvangelder.db.artist.ArtistRepository;
import bertvangelder.db.artist.ArtistRepositoryFactory;
import bertvangelder.db.concert.ConcertRepository;
import bertvangelder.db.concert.ConcertRepositoryFactory;
import bertvangelder.db.venue.VenueRepository;
import bertvangelder.db.venue.VenueRepositoryFactory;
import bertvangelder.model.Artist;
import bertvangelder.model.Concert;
import bertvangelder.model.Venue;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by Bert on 18/04/2017.
 */
public class ConcertService {
    private ArtistRepository artistRepository;
    private VenueRepository venueRepository;
    private ConcertRepository concertRepository;

    /**
     * Constructor of ConcertService.
     * Creates repositories for all model classes.
     * If a connection to a local SQL database can be made (port 3306, user:root, no password),
     * it will use this database, else it will use memory as database.
     * Also fills the concert repository with some values for testing purposes.
     * @throws DatabaseException
     */
    public ConcertService() throws DatabaseException {
        ArtistRepositoryFactory artistRepositoryFactory = ArtistRepositoryFactory.getInstance();
        VenueRepositoryFactory venueRepositoryFactory = VenueRepositoryFactory.getInstance();
        ConcertRepositoryFactory concertRepositoryFactory = ConcertRepositoryFactory.getInstance();

        System.out.println("------------------------------------------------------------------------");
        try {
            //test sql connection, if no connection use the memory as database
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ConcertDB","root","");
            artistRepository = artistRepositoryFactory.createArtistRepository("sql");
            venueRepository = venueRepositoryFactory.createVenueRepository("sql");
            concertRepository = concertRepositoryFactory.createConcertRepository("sql");
            System.out.println("Using sql database");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Using memory database");
            artistRepository = artistRepositoryFactory.createArtistRepository("memory");
            venueRepository = venueRepositoryFactory.createVenueRepository("memory");
            concertRepository = concertRepositoryFactory.createConcertRepository("memory");
        }
        System.out.println("------------------------------------------------------------------------");
        //adds some concerts to the concertRepository for testing purposes
        fillRepository(concertRepository);
    }

    private void fillRepository(ConcertRepository concertRepository) throws DatabaseException {
        ArrayList<Concert> concerts = concertRepository.readAllConcerts();
        if(concerts.isEmpty()) {
            concertRepository.addConcert(new Concert("Volbeat Returns",
                    new Artist("Volbeat"),
                    new Venue("Ancienne Belgique", "Brussels"),
                    LocalDate.of(2017, 5, 13),
                    LocalTime.of(20, 0)));
            concertRepository.addConcert(new Concert("WorldWired",
                    new Artist("Metallica"),
                    new Venue("Sportpaleis", "Antwerp"),
                    LocalDate.of(2017, 6, 10),
                    LocalTime.of(21, 0)));
            concertRepository.addConcert(new Concert("Drones World Tour",
                    new Artist("Muse"),
                    new Venue("Apollon ", "Antwerp"),
                    LocalDate.of(2017, 10, 28),
                    LocalTime.of(20, 30)));
            concertRepository.addConcert(new Concert("Humbug Tour",
                    new Artist("Arctic Monkeys"),
                    new Venue("Vorst Nationaal", "Brussels"),
                    LocalDate.of(2017, 8, 12),
                    LocalTime.of(21, 0)));
            concertRepository.addConcert(new Concert("Laibach",
                    new Artist("Rammstein"),
                    new Venue("Het Depot", "Leuven"),
                    LocalDate.of(2017, 6, 15),
                    LocalTime.of(19, 30)));
        }
    }

    public ArrayList<Concert> findAllConcerts(){
        return concertRepository.readAllConcerts();
    }

    public Concert findConcertById(long id) throws DatabaseException {
        return concertRepository.readConcert(id);
    }

    public void saveConcert(Concert concert) throws DatabaseException {
        concertRepository.addConcert(concert);
    }

    public void updateConcert(Concert concert) throws DatabaseException {
        concertRepository.updateConcert(concert);
    }

    public void deleteConcertById(long id) throws DatabaseException {
        concertRepository.deleteConcert(id);
    }

    public ArrayList<Artist> findAllArtists(){
        return artistRepository.readAllArtists();
    }

    public Artist findArtistById(long id) throws DatabaseException {
        return artistRepository.readArtist(id);
    }

    public void saveArtist(Artist artist) throws DatabaseException {
        artistRepository.addArtist(artist);
    }

    public void updateArtist(Artist artist) throws DatabaseException {
        artistRepository.updateArtist(artist);
    }

    public void deleteArtistById(long id) throws DatabaseException {
        artistRepository.deleteArtist(id);
    }

    public ArrayList<Venue> findAllVenues(){
        return venueRepository.readAllVenues();
    }

    public Venue findVenueById(long id) throws DatabaseException {
        return venueRepository.readVenue(id);
    }

    public void saveVenue(Venue venue) throws DatabaseException {
        venueRepository.addVenue(venue);
    }

    public void updateVenue(Venue venue) throws DatabaseException {
        venueRepository.updateVenue(venue);
    }

    public void deleteVenueById(long id) throws DatabaseException {
        venueRepository.deleteVenue(id);
    }

}
