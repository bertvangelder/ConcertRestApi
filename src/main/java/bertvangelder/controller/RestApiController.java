package bertvangelder.controller;

import bertvangelder.db.DatabaseException;
import bertvangelder.model.Concert;
import bertvangelder.service.ConcertService;
import bertvangelder.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by Bert on 13/04/2017.
 */
@RestController
@RequestMapping("/api")
public class RestApiController {
    private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
    private ConcertService concertService;

    public RestApiController(ConcertService concertService){
        this.concertService = concertService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/concerts")
    public ResponseEntity<List<Concert>> getConcerts() {
        logger.info("Listing all concerts");
        List<Concert> concerts = concertService.findAllConcerts();
        if (concerts.isEmpty()) {
            return new ResponseEntity<List<Concert>>(concerts, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Concert>>(concerts, HttpStatus.OK);
    }

    @RequestMapping(value = "/concerts/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getConcert(@PathVariable("id") long id) {
        try {
            Concert concert = concertService.findConcertById(id);
            return new ResponseEntity<Concert>(concert, HttpStatus.OK);
        } catch (DatabaseException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(new CustomErrorType(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/concerts", method = RequestMethod.POST)
    public ResponseEntity<?> createConcert(@RequestBody Concert concert, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Concert : {}", concert);

        try {
            concertService.saveConcert(concert);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/concert/{id}").buildAndExpand(concert.getConcertId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (DatabaseException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(new CustomErrorType(ex.getMessage()),HttpStatus.CONFLICT);
        }
    }


    @RequestMapping(value = "/concerts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateConcert(@RequestBody Concert concert) {
        logger.info("Updating Concert with id {}", concert.getConcertId());

        try {
            concertService.updateConcert(concert);
            return new ResponseEntity<Concert>(concert, HttpStatus.OK);
        } catch (DatabaseException ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(new CustomErrorType(ex.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/concerts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteConcert(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Concert with id {}", id);
        try{
            concertService.deleteConcertById(id);
            return new ResponseEntity<Concert>(HttpStatus.NO_CONTENT);
        } catch(DatabaseException ex){
            logger.error(ex.getMessage());
            return new ResponseEntity<>(new CustomErrorType(ex.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

}
