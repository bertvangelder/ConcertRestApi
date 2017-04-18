package bertvangelder;

import bertvangelder.service.ConcertService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import bertvangelder.db.DatabaseException;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConcertApp {

    @Bean
    public ConcertService concertService() throws DatabaseException{
        return new ConcertService();
    }


    public static void main(String[] args) {
        SpringApplication.run(ConcertApp.class, args);
    }
}