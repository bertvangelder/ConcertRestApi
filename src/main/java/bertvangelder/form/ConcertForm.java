package bertvangelder.form;


import bertvangelder.validator.Future;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;


public class ConcertForm {
    @Size(min = 4, max = 100, message
            = "The name must be between 4 and 100 characters")
    private String name;
    private long artistId;
    private long venueId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    @Future(message = "The date must be in the future")
    private LocalDate date;
    private LocalTime time;

    public long getArtistId() {
        return artistId;
    }

    public String getName() {
        return name;
    }

    public long getVenueId() {
        return venueId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public void setVenueId(long venueId) {
        this.venueId = venueId;
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
