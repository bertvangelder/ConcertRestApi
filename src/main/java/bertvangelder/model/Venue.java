package bertvangelder.model;

import javax.persistence.*;

@Entity
@Table(name = "VENUE")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long venueId;
    private String name;
    private String location;

    public Venue() {
    }

    public Venue(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Venue(Venue venue) {
        this.venueId = venue.getVenueId();
        this.name = venue.getName();
        this.location = venue.getLocation();
    }

    public long getVenueId() {
        return venueId;
    }

    public void setVenueId(long id) {
        this.venueId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
