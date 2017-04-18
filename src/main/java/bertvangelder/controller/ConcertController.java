package bertvangelder.controller;

import bertvangelder.model.Concert;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Bert on 13/04/2017.
 */
@Controller
@RequestMapping("/")
public class ConcertController {
    private static final String REST_SERVICE_URI = "http://localhost:8080/api";

    @GetMapping
    public ModelAndView list(){
        //using the rest service to find all concerts
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Concert[]> responseEntity = restTemplate.getForEntity(REST_SERVICE_URI + "/concerts", Concert[].class);
        Concert[] concerts = responseEntity.getBody();

        ArrayList<Concert> concertList = new ArrayList<Concert>(Arrays.asList(concerts));

        return new ModelAndView("concerts", "concerts", concertList);
    }
}
