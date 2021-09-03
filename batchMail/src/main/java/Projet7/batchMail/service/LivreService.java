package Projet7.batchMail.service;

import Projet7.batchMail.dto.LivreDTO;
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
import java.util.List;

@Data
@Service
public class LivreService {


    Logger logger = (Logger) LoggerFactory.getLogger(LivreService.class);

    private List<LivreDTO> itemLivresDisponibles = new ArrayList<LivreDTO>();


    @Autowired
    public AuthService authService;


    /*Methode pour obtenir tous les livres disponibles de la base de données de l'API rest*/
    public List<LivreDTO> getAllLivresDisponibles() throws IOException, ParseException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/livre/disponibles"))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<LivreDTO> tousLivresDisponibles = mapper.readValue(response.body(), new TypeReference<List<LivreDTO>>() {
        });
        if (tousLivresDisponibles.size() > 0) {
            logger.info(" retour liste tousLivresDisponibles car la taille de laliste >0 " + tousLivresDisponibles);
            logger.info(" valeur du premier livre disponible " + tousLivresDisponibles.get(0));
            logger.info(" valeur de l'auteur du premier livre " + tousLivresDisponibles.get(0).getAuteur());
            return tousLivresDisponibles;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste tousLivres ");
            return new ArrayList<LivreDTO>();
        }
    }

    /*Methode pour modifier un livre à l'API rest*/
    public LivreDTO modifierUnLivre(LivreDTO livre) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(livre);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/livre/"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<LivreDTO>() {
        });

    }





}
