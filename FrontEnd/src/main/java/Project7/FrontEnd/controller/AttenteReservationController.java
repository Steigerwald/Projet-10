package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.AttenteReservationDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.form.LivreForm;
import Project7.FrontEnd.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/attenteReservation")
public class AttenteReservationController {

    @Autowired
    AttenteReservationService attenteReservationService;

    @Autowired
    public LivreService livreService;

    @Autowired
    public AuthService authService;

    @Autowired
    public ResponseService responseService;

    @Autowired
    public UserService userService;


    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationController.class);

    /* controller pour avoir toutes les reservations*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllAttenteReservations(Model model, Principal principal) throws IOException, InterruptedException {
        List<AttenteReservationDTO> listeAttenteReservations = attenteReservationService.getAllAttenteReservations();
        if(authService.getUserConnecte()!=null){
            model.addAttribute("role",authService.getUserConnecte().getRole().getNomRole());
        }else{
            model.addAttribute("role",null);
        }
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("attenteReservations",listeAttenteReservations);
        return "attenteReservation/listeAttenteReservations";
    }


    @RequestMapping(value="/annuler/{id}",method = RequestMethod.POST)
    public String attenteReservationAnnuler(Model model, Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {
        AttenteReservationDTO attenteReservationAAnnuler =attenteReservationService.getAttenteReservationById(id);
        AttenteReservationDTO attenteReservationAnnulee = attenteReservationService.annulerAttenteReservation(attenteReservationAAnnuler);
        //model.addAttribute("reservation",reservationRetiree);
        return "redirect:/user/EspacePersonnel";
    }

    /* controller pour creer une attente de réservation d'un livre pour l'API*/
    @RequestMapping(value="/creerAttente/livre/{id}",method = RequestMethod.POST)
    public String reservationLivre(Model model,Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {
        UserDTO userConnecte =authService.getUserConnecte();
        LivreDTO livreConcerne = livreService.getLivreById(id);
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("livre",livreConcerne);
        Boolean userDansListeAttente =attenteReservationService.getVerificationUserIsPresentInListeAttente(id,userConnecte.getIdUser());
        if (userDansListeAttente){
        AttenteReservationDTO newAttentereservation = new AttenteReservationDTO();
        newAttentereservation.setUser(userConnecte);
        newAttentereservation.setTitreLivre(livreConcerne.getTitre());
        AttenteReservationDTO attenteReservation =attenteReservationService.createAttenteReservation(newAttentereservation);
        userService.verifierUserConnecte(model);
        model.addAttribute("attenteReservation",attenteReservation);
        return "attenteReservation/demandeAttenteReservation";
        }else{
        AttenteReservationDTO attenteReservationTrouvee =attenteReservationService.getAttenteReservationByIdLivreAndByIdUser(livreConcerne.getIdLivre(),userConnecte.getIdUser());
        model.addAttribute("attenteReservation",attenteReservationTrouvee);
        return "attenteReservation/demandeAttenteReservationRefusee";
        }
    }
}
