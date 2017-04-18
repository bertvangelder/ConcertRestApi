package bertvangelder.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.LocalDate;

@Entity
@Table(name = "Concert")
public class Concert  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long concertId;
    private String name;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "artistId")
    private Artist artist;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "venueId")
    private Venue venue;
    private LocalDate date;
    private LocalTime time;

    public Concert() {
    }

    public Concert(String name, Artist artist, Venue venue, LocalDate date, LocalTime time) {
        this.name = name;
        this.artist = artist;
        this.venue = venue;
        this.date = date;
        this.time = time;
    }

    public long getConcertId() {
        return concertId;
    }

    public void setConcertId(long id) {
        this.concertId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
