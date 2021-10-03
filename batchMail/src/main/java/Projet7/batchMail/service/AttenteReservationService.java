package Projet7.batchMail.service;


import Projet7.batchMail.dto.AttenteReservationDTO;
import Projet7.batchMail.dto.LivreDTO;
import Projet7.batchMail.dto.UserDTO;
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
import java.util.List;

@Data
@Service
public class AttenteReservationService {

    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationService.class);

    private List<UserDTO> itemUsersAttente = new ArrayList<UserDTO>();


    @Autowired
    public AuthService authService;


    /*Methode pour annuler une attente de reservation */
    public void annulerAttenteReservation(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/"+id))
                .header("Authorization","Bearer"+" "+token)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());
    }

    /*Methode pour avoir la liste d'attente des users pour un livre*/
    public List<UserDTO> getAllAttenteUsersByIdLivre(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/Users/livre/"+id))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<UserDTO> tousUserEnAttenteByLivre = mapper.readValue(response.body(), new TypeReference<List<UserDTO>>() {
        });
        if (tousUserEnAttenteByLivre.size() > 0) {
            logger.info(" retour liste tousUserEnAttenteByLivre car la taille de laliste >0 " + tousUserEnAttenteByLivre);
            return tousUserEnAttenteByLivre;
        } else {
            logger.info(" retour d'une nouvelle liste d'attente car pas de User dans la liste tousUserEnAttenteByLivre ");
            return new ArrayList<UserDTO>();
        }
    }

    /*Methode pour obtenir une attente de reservation par IdLivre et par IdUser*/
    public AttenteReservationDTO getAttenteReservationByIdLivreAndByIdUser(int idLivre, int idUser) throws IOException, ParseException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/user/livre/"+idLivre+"&&"+idUser))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<AttenteReservationDTO>(){});
    }



}
