package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class BibliothequeService {

    Logger logger = (Logger) LoggerFactory.getLogger(BibliothequeService.class);

    @Autowired
    public AuthService authService;

    @Autowired
    public ResponseService responseService;

    /*Methode pour obtenir toutes les bibliotheques de la base de données de l'API rest*/
    public List<BibliothequeDTO> getAllBibliotheques() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/bibliotheque/"))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        if (response.statusCode()==200) {
            ObjectMapper mapper = new ObjectMapper();
            List<BibliothequeDTO> toutesBibliotheques = mapper.readValue(response.body(), new TypeReference<List<BibliothequeDTO>>() {
            });
            if (toutesBibliotheques.size() > 0) {
                logger.info(" retour liste toutesBibliotheques car la taille de laliste >0 " + toutesBibliotheques);
                return toutesBibliotheques;
            } else {
                logger.info(" retour d'une nouvelle liste car pas d'élément dans la liste toutesBibliotheques ");
                return new ArrayList<BibliothequeDTO>();
            }
        }
        return new ArrayList<BibliothequeDTO>();
    }

    /*Methode pour obtenir une bibliotheque par Id de l'API rest*/
    public BibliothequeDTO getBibliothequeById(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String token = authService.getMemoireToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/bibliotheque/"+id))
                .header("Authorization","Bearer"+" "+token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body " + response.body());
        responseService.setResponseStatut(response.statusCode());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        BibliothequeDTO bibliothequeTrouvee = mapper.readValue(response.body(), new TypeReference<BibliothequeDTO>() {
        });

        if(bibliothequeTrouvee!= null) {
            logger.info(" retour de la bibliotheque car elle existe "+bibliothequeTrouvee);
            return bibliothequeTrouvee;
        } else {
            logger.info(" retour de null car il n'y a pas de bibliotheque avec cet id");
            return null;
        }
    }

    /*Methode pour transformer un bibliothequeForm en bibliothequeDTO
    public BibliothequeDTO transformerBibliothequeFormEnBibliothequeDTO(BibliothequeForm bibliothequeForm){
        BibliothequeDTO bibliothequeDTO = new BibliothequeDTO();
        bibliothequeDTO.setIdBibliotheque(bibliothequeForm.getIdBibliotheque());
        bibliothequeDTO.setNomBibliotheque(bibliothequeForm.getNomBibliotheque());
        bibliothequeDTO.setLieu(bibliothequeForm.getLieu());
        bibliothequeDTO.setAdresse(bibliothequeForm.getAdresse());

        return bibliothequeDTO;
    }*/



}
