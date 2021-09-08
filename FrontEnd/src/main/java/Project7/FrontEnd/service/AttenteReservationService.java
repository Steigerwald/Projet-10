package Project7.FrontEnd.service;


import Project7.FrontEnd.dto.AttenteReservationDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class AttenteReservationService {

    @Autowired
    public UserService userService;

    @Autowired
    public AuthService authService;

    @Autowired
    public ResponseService responseService;

    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationService.class);

    /*Methode pour obtenir toutes les attentes de reservation de la base de données de l'API rest*/
    public List<AttenteReservationDTO> getAllAttenteReservations() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/"))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<AttenteReservationDTO> toutesAttenteReservations = mapper.readValue(response.body(), new TypeReference<List<AttenteReservationDTO>>() {
        });
        if(toutesAttenteReservations.size() > 0) {
            logger.info(" retour liste toutesAttenteReservations car la taille de laliste >0 "+toutesAttenteReservations);
            return toutesAttenteReservations;
        } else {
            logger.info(" retour d'une nouvelle liste d(attente car pas d'élément dans la liste toutesAttenteReservations ");
            return new ArrayList<AttenteReservationDTO>();
        }
    }

    /*Methode pour avoir toutes les attentes de reservation d'un livre*/
    public List<AttenteReservationDTO> getAllAttenteReservationsByIdLivre(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/livre/"+id))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<AttenteReservationDTO> toutesAttenteReservationsByLivre = mapper.readValue(response.body(), new TypeReference<List<AttenteReservationDTO>>() {
        });
        if (toutesAttenteReservationsByLivre.size() > 0) {
            logger.info(" retour liste toutesAttenteReservationsByLivre car la taille de laliste >0 " + toutesAttenteReservationsByLivre);
            return toutesAttenteReservationsByLivre;
        } else {
            logger.info(" retour d'une nouvelle liste d'attente car pas d'élément dans la liste toutesAttenteReservationsByLivre ");
            return new ArrayList<AttenteReservationDTO>();
        }
    }

    /*Methode pour avoir toutes les attentes de reservation d'un user*/
    public List<AttenteReservationDTO> getAllAttenteReservationsByIdUser(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/user/"+id))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<AttenteReservationDTO> toutesAttenteReservationsByUser = mapper.readValue(response.body(), new TypeReference<List<AttenteReservationDTO>>() {
        });
        if (toutesAttenteReservationsByUser.size() > 0) {
            logger.info(" retour liste toutesAttenteReservationsByLivre car la taille de laliste >0 " + toutesAttenteReservationsByUser);
            return toutesAttenteReservationsByUser;
        } else {
            logger.info(" retour d'une nouvelle liste d'attente car pas d'élément dans la liste toutesAttenteReservationsByLivre ");
            return new ArrayList<AttenteReservationDTO>();
        }
    }

    /*Methode pour obtenir une attente de reservation */
    public AttenteReservationDTO getAttenteReservationById(int id) throws IOException, ParseException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/"+id))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        AttenteReservationDTO attenteReservationById = mapper.readValue(response.body(), new TypeReference<AttenteReservationDTO>() {
        });
        if(attenteReservationById!=null) {
            logger.info(" l'id de l'attente de reservation trouvée est :  "+attenteReservationById.getIdAttenteReservation());
            return attenteReservationById;
        } else {
            logger.info(" retour de nul car pas d'élément et de reservation trouvée ");
            return null;
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
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<AttenteReservationDTO>(){});
    }


    /*Methode pour creer une attente de reservation */
    public AttenteReservationDTO createAttenteReservation(AttenteReservationDTO attenteReservation) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(attenteReservation);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/addAttenteReservation"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" on est passé par la méthode du front end");
        logger.info(" reponse du body "+response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<AttenteReservationDTO>(){});
    }

    /*Methode pour effacer une attente de reservation de la base de données */
    public void effacerUneAttenteReservation(AttenteReservationDTO attenteReservation) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/" + attenteReservation.getIdAttenteReservation()))
                //        .header("Content-Type", "application/json")
                .header("Authorization","Bearer"+" "+token)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
    }

    /*Methode pour annuler une attente de reservation */
    public AttenteReservationDTO annulerAttenteReservation(AttenteReservationDTO attenteReservation) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(attenteReservation);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/annulerAttenteReservation"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<AttenteReservationDTO>(){});
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
        responseService.setResponseStatut(response.statusCode());
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

    /*Methode pour avoir la position d'un user dans une liste d'attente d'un livre*/
    public int getPositionByIdLivreAndForIdUser(int id,int idUser) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/User/Position/livre/"+id+"&&"+idUser))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        int position = Integer.parseInt(response.body());
        if (position > 0) {
            logger.info(" la position est >0 " + position);
            return position;
        } else {
            logger.info(" la position est nulle car le user n'est pas dans la liste d'attente ");
            return position;
        }
    }

    /* méthode pour vérifier que le user est présent dans liste d'attente*/
    public Boolean getVerificationUserIsPresentInListeAttente(int idLivre,int idUser) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/User/VerifierAttenteReservation/livre/"+idLivre+"&&"+idUser))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        Boolean verification;
        if (response.body().compareTo("true")==0) {
            verification = true;
        }else{
            verification=false;
        }
        return verification;
    }

    /* méthode pour vérifier la liste d'attente est suffisante par rapport aux nombre d'exemplaires*/
    public Boolean getVerificationListeAttenteSuffisante(int idLivre) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/attenteReservation/nombre/livre/"+idLivre))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        Boolean verification;
        if (response.body().compareTo("true")==0) {
            verification = true;
        }else{
            verification=false;
        }
        return verification;
    }


    /* méthode pour vérifier que le user n'a pas déjà une reservation du livre*/





}
