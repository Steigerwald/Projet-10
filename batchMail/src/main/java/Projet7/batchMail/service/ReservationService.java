package Projet7.batchMail.service;

import Projet7.batchMail.dto.ReservationDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Service
public class ReservationService {

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationService.class);

    private List<ReservationDTO> itemReservations = new ArrayList<ReservationDTO>();

    private List<ReservationDTO> itemInfoReservations = new ArrayList<ReservationDTO>();

    private List<ReservationDTO> reservationsRelancees = new ArrayList<ReservationDTO>();

    private List<ReservationDTO> itemFinReservations = new ArrayList<ReservationDTO>();

    @Autowired
    public AuthService authService;


    /*Methode pour obtenir une reservation par son id de la base de données de l'API rest*/
    public ReservationDTO getReservationById(int id) throws IOException, ParseException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/"+id))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        ReservationDTO reservationById = mapper.readValue(response.body(), new TypeReference<ReservationDTO>() {
        });
        if(reservationById!=null) {
            logger.info(" l'id de la reservation trouvée est :  "+reservationById.getIdReservation());
            return reservationById;
        } else {
            logger.info(" retour de nul car pas d'élément et de reservation trouvée ");
            return null;
        }
    }

    /*Methode pour obtenir toutes les reservations du batch à relancer de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservationsBatch() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/all/batch"))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservations = mapper.readValue(response.body(), new TypeReference<List<ReservationDTO>>() {
        });
        if(toutesReservations.size() > 0) {
            logger.info(" retour liste toutesReservations car la taille de laliste >0 "+toutesReservations);
            return toutesReservations;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesReservations ");
            return new ArrayList<ReservationDTO>();
        }
    }

    /*Methode pour modifier la reservation du batch (la relance) de la base de données de l'API rest*/
    public ReservationDTO modifyReservationBatch(ReservationDTO reservation) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(reservation);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
    }


    /*Methode pour creer une réservation à l'API rest*/
    public ReservationDTO createReservation(ReservationDTO reservation) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(reservation);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/addReservation"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
    }

    /*Methode pour effacer une reservation de la base de données */
    public void effacerUneReservation(ReservationDTO reservation) throws IOException, InterruptedException {
        int id = reservation.getIdReservation();
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/"+id))
                .header("Authorization","Bearer"+" "+token)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
    }

    /*Methode pour avoir toutes les réservation en attente retrait et qui n'ont pas de date d'infoMail*/
    public List<ReservationDTO> getAllReservationsEnAttenteInfoMail() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/all/infoReservation"))
                .header("Authorization", "Bearer" + " " + token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservations = mapper.readValue(response.body(), new TypeReference<List<ReservationDTO>>() {
        });
        if (toutesReservations.size() > 0) {
            logger.info(" retour liste toutesReservations car la taille de laliste InfoReservation >0 " + toutesReservations);
            return toutesReservations;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste InfoReservation ");
            return new ArrayList<ReservationDTO>();
        }
    }

    /*Methode pour avoir toutes les réservation en attente retrait et qui n'ont pas été retirée et dont la date de retrait est dépassé*/
    public List<ReservationDTO> getAllReservationsEnAttenteDateRetraitNull() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/all/dateRetraitNull"))
                .header("Authorization", "Bearer" + " " + token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body de getAllReservationsEnAttenteDateRetraitNull " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservationsDateRetrait = mapper.readValue(response.body(), new TypeReference<List<ReservationDTO>>() {
        });
        if (toutesReservationsDateRetrait.size() > 0) {
            logger.info(" retour liste toutesReservationsDateRetrait car la taille de laliste DateRetraitNull >0 " + toutesReservationsDateRetrait);
            return toutesReservationsDateRetrait;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste DateRetraitNull ");
            return new ArrayList<ReservationDTO>();
        }
    }


}
