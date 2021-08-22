package Project7.FrontEnd.service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Data
@Service
public class ResponseService {

    Logger logger = (Logger) LoggerFactory.getLogger(ResponseService.class);

    private int responseStatut;

    /*Methode pour gérer la réponse négative ou autre réponse en cas de non identification*/
    public String gestionDeReponseHttp (int reponse,String direction) {

        switch (reponse) {
            case 404:
                return "home/page404";
            case 403:
                return "redirect:/user/login";
            case 500:
                return "home/page404";
        }
        return direction;
    }
}
