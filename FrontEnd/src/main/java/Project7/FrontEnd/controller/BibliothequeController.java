package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.service.AuthService;
import Project7.FrontEnd.service.BibliothequeService;
import Project7.FrontEnd.service.ResponseService;
import Project7.FrontEnd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/bibliotheque")
public class BibliothequeController {

    @Autowired
    public BibliothequeService bibliothequeService;

    @Autowired
    public AuthService authService;

    @Autowired
    public ResponseService responseService;

    @Autowired
    public UserService userService;

    /* controller pour avoir toutes les bibliotheques*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllBibliotheques(Model model) throws IOException, InterruptedException {
        List<BibliothequeDTO> bibliotheques = bibliothequeService.getAllBibliotheques();
        userService.verifierUserConnecte(model);
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("bibliotheques",bibliotheques);
        return responseService.gestionDeReponseHttp(responseService.getResponseStatut(),"bibliotheque/listeBibliotheques");
    }

}
