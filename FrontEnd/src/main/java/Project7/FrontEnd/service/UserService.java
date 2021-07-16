package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.form.LoginForm;
import Project7.FrontEnd.form.UserForm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserService {

    @Autowired
    public AuthService authService;

    @Autowired
    public ResponseService responseService;

    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    /*Methode pour obtenir un user par Id de l'API rest*/
    public UserDTO getUserById(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/user/"+id))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        System.out.println(response.body());
        responseService.setResponseStatut(response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        UserDTO userTrouve = mapper.readValue(response.body(), new TypeReference<UserDTO>() {
        });
        if(userTrouve!= null) {
            logger.info(" retour du user car il existe "+userTrouve);
            return userTrouve;
        } else {
            logger.info(" retour de null car il n'y a pas de bibliotheque avec cet id");
            return null;
        }
    }

    /*Methode pour obtenir un user par mail de l'API rest*/
    public UserDTO getUserByMail(UserDTO userDTO) throws IOException, InterruptedException {
/*
        var values = new HashMap<String, String>() {{
            put("username", "admin@gmail");
        }};*/
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(userDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/user/me"))
                .headers("Content-Type", "application/json","Authorization","Bearer"+" "+token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<UserDTO>(){});
    }


    /*Methode pour envoyer le login et récupérer le token d'autorisation*/
    public String getTokenByMailAndMotDePasse(LoginForm utilisateur) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestUtilisateur = objectMapper
                .writeValueAsString(utilisateur);
        logger.info(" valeur du string envoyé "+utilisateur);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestUtilisateur))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        logger.info(" retour du body: "+response.body());
        responseService.setResponseStatut(response.statusCode());
        return response.body();
    }

    /*Methode pour envoyer le user et creer le compte dans la base de données*/
    public UserDTO createUserDansBaseDeDonnees(UserDTO utilisateur) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestUtilisateur = objectMapper
                .writeValueAsString(utilisateur);
        logger.info(" valeur du string envoyé "+utilisateur);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/user/addUser"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestUtilisateur))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        logger.info(" retour du body: "+response.body());
        responseService.setResponseStatut(response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<UserDTO>() {
        });
    }

    /*Methode pour transformer un UserFormen en UserDTO*/
    public UserDTO transformerUserFormEnUserDTO(UserForm userForm) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(userForm.getIdUser());
        userDTO.setNomUser(userForm.getNomUser());
        userDTO.setPrenomUser(userForm.getPrenomUser());
        userDTO.setMailUser(userForm.getMailUser());
        userDTO.setMotDePasse(userForm.getMotDePasse());
        return userDTO;
    }

    /*Methode pour vérifier autehentification pour le role*/
    public void verifierUserConnecte(Model model) {
        if (authService.getUserConnecte() != null) {
            model.addAttribute("role", authService.getUserConnecte().getRole().getNomRole());
        } else {
            model.addAttribute("role", null);
        }
    }
}
