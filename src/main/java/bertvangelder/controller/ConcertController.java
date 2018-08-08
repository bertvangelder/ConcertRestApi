package bertvangelder.controller;

import bertvangelder.db.DatabaseException;
import bertvangelder.form.ConcertForm;
import bertvangelder.model.Artist;
import bertvangelder.model.Concert;
import bertvangelder.model.Venue;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import bertvangelder.service.ConcertService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bert on 13/04/2017.
 */
@Controller
@RequestMapping("/")
public class ConcertController {
    private static final String REST_SERVICE_URI = "http://localhost:8080/api";
    private ConcertService concertService;

    public ConcertController(ConcertService concertService){
        this.concertService = concertService;
    }

    @GetMapping
    public ModelAndView list(){
        //using the rest service to find all concerts
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Concert[]> responseEntity = restTemplate.getForEntity(REST_SERVICE_URI + "/concerts", Concert[].class);
        Concert[] concerts = responseEntity.getBody();

        ArrayList<Concert> concertList = new ArrayList<Concert>(Arrays.asList(concerts));

        return new ModelAndView("concerts", "concerts", concertList);
    }

    @GetMapping("/addConcert")
    public String concertForm(ConcertForm concertForm) {
        return "concert";
    }

    @PostMapping("/addConcert")
    public String concertSubmit(@Valid ConcertForm concertForm, BindingResult bindingResult ) {
        if (bindingResult.hasErrors()) {
            return "concert";
        }
        Concert concert = new Concert();
        concert.setName(concertForm.getName());
        concert.setDate(concertForm.getDate());
        concert.setTime(concertForm.getTime());
        try{
            concert.setArtist(concertService.findArtistById(concertForm.getArtistId()));
            concert.setVenue(concertService.findVenueById(concertForm.getVenueId()));
            concertService.saveConcert(concert);
        } catch (DatabaseException ex) {
            return "redirect:/";
        }
        return "redirect:/";
    }

    @ModelAttribute("allVenues")
    public ArrayList<Venue> getAllVenues() {
        return concertService.findAllVenues();
    }

    @ModelAttribute("allArtists")
    public ArrayList<Artist> getAllArtits() {
        return concertService.findAllArtists();
    }
}
