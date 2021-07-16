package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.form.ReservationForm;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    public UserService userService;

    @Autowired
    public AuthService authService;

    @Autowired
    public ResponseService responseService;

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationService.class);

    /*Methode pour obtenir toutes les reservations de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservations() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/"))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
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

    /*Methode pour transformer une reservationForm en reservationDTO*/
    public ReservationDTO transformerReservationFormEnReservationDTO(ReservationForm reservationForm,UserDTO userDTO) throws ParseException, IOException {
        ReservationDTO reservationDTO = new ReservationDTO ();
        reservationDTO.setIsactif(true);
        reservationDTO.setUser(userDTO);
        return reservationDTO;
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
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
    }

    /*Methode pour obtenir une réservation disponible de la base de données de l'API rest*/
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
        responseService.setResponseStatut(response.statusCode());
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

    /*Methode pour obtenir toutes les reservations à valider de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservationsAValider() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/AValider"))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservationsAValider = mapper.readValue(response.body(), new TypeReference<List<ReservationDTO>>() {
        });
        if (toutesReservationsAValider.size() > 0) {
            logger.info(" retour liste toutesReservations car la taille de laliste >0 " + toutesReservationsAValider);
            return toutesReservationsAValider;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesReservations ");
            return new ArrayList<ReservationDTO>();
        }
    }

    /*Methode pour obtenir toutes les reservations en cours de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservationsEnCours() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/EnCours"))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservationsEnCours = mapper.readValue(response.body(), new TypeReference<List<ReservationDTO>>() {
        });
        if (toutesReservationsEnCours.size() > 0) {
            logger.info(" retour liste toutesReservationsEnCours car la taille de laliste >0 " + toutesReservationsEnCours);
            return toutesReservationsEnCours;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesReservationsEnCours ");
            return new ArrayList<ReservationDTO>();
        }
    }

    /*Methode pour obtenir toutes les reservations d'un user en cours de la base de données de l'API rest*/
    public List<ReservationDTO> getAllReservationsEnCoursByUser(UserDTO user) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/byUser"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        List<ReservationDTO> toutesReservationsByUser = mapper.readValue(response.body(), new TypeReference<List<ReservationDTO>>() {
        });
        if (toutesReservationsByUser.size() > 0) {
            logger.info(" retour liste toutesReservationsByUser car la taille de laliste >0 " + toutesReservationsByUser);
            return toutesReservationsByUser;
        } else {
            logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesReservationsByUser ");
            return new ArrayList<ReservationDTO>();
        }
    }


        /*Methode pour retirer une reservation de la base de données de l'API rest*/
        public ReservationDTO retirerReservation(ReservationDTO reservation) throws IOException, InterruptedException {
            Date today = new Date();
            reservation.setDateDeRetrait(today);
            reservation.setEtatReservation("en cours de pret");
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
            responseService.setResponseStatut(response.statusCode());
            System.out.println(response.body());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
        }

    /*Methode pour enregistrer un retour d'une reservation de la base de données de l'API rest*/
    public ReservationDTO retournerReservation(ReservationDTO reservation) throws IOException, InterruptedException {
        Date today = new Date();
        reservation.setDateDeRetour(today);
        reservation.setEtatReservation("retournée");
        reservation.setIsactif(false);
        reservation.getLivre().setDisponibilite(true);
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
        responseService.setResponseStatut(response.statusCode());
        String requestBody02=objectMapper
                .writeValueAsString(reservation.getLivre());
        HttpRequest request02 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/livre/"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody02))
                .build();
        HttpResponse<String> response02 = client.send(request02, HttpResponse.BodyHandlers.ofString());
        responseService.setResponseStatut(response.statusCode());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
    }


    /*Methode pour vérifier la date limite de prêt d'une reservation de la base de données de l'API rest*/
    public ReservationDTO verifierReservation(ReservationDTO reservation) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(reservation);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/reservation/verifierReservation"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>() {});
    }

    /*Methode pour vérifier une liste de réservation*/
    public List<ReservationDTO> verifierListeReservations(List<ReservationDTO> listeReservations) throws IOException, InterruptedException {
        List<ReservationDTO> newList = new ArrayList<>();
        for (ReservationDTO reservation:listeReservations){
            newList.add(verifierReservation(reservation));
        }
        return newList;
    }

    /*Methode pour calculer la date limite de retour du livre en fonction de la date de retrait*/
    public List<String> calculerDateLimitesDeretraitDUneListeDeReservation(List<ReservationDTO> listeReservations){
        List<String> newListDates = new ArrayList<>();
        SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd-MM-yyyy");
        for (ReservationDTO reservation:listeReservations){
           if (reservation.getDateDeRetrait()!=null) {
               if (!reservation.getProlongation()) {
                   Calendar c = Calendar.getInstance();
                   c.setTime(reservation.getDateDeRetrait());
                   c.add(Calendar.DATE, reservation.getDelaiDeLocation());
                   Date currentDatePlusOne = c.getTime();
                   System.out.println(simpleDateFormat02.format(currentDatePlusOne));
                   newListDates.add(simpleDateFormat02.format(currentDatePlusOne));
               } else {
                   Calendar c = Calendar.getInstance();
                   c.setTime(reservation.getDateDeRetrait());
                   c.add(Calendar.DATE, (reservation.getDelaiDeLocation() * 2));
                   Date currentDatePlusTwo = c.getTime();
                   System.out.println(simpleDateFormat02.format(currentDatePlusTwo));
                   newListDates.add(simpleDateFormat02.format(currentDatePlusTwo));
               }
           } else {
               newListDates.add("pas encore retiré");
           }
        }
        return newListDates;
    }

    /*Methode pour prolonger une reservation de la base de données de l'API rest*/
    public ReservationDTO prolongerReservation(ReservationDTO reservation) throws IOException, InterruptedException {
        reservation.setProlongation(true);
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
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<ReservationDTO>(){});
    }

}
