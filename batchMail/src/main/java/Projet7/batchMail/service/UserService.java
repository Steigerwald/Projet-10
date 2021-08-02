package Projet7.batchMail.service;

import Projet7.batchMail.form.LoginForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserService {

    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    /*Methode pour envoyer le login et récupérer le token d'autorisation*/
    public String getTokenByMailAndMotDePasse(LoginForm utilisateur) throws IOException, InterruptedException,NullPointerException{
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
        String reponse ="1";
        if(response.body() != null) {
            reponse=response.body();
        }else {
            reponse=null;
        }
        return reponse;
    }






}
