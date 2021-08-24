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
        AttenteReservationDTO newAttentereservation = new AttenteReservationDTO();
        newAttentereservation.setUser(userConnecte);
        LivreDTO livreConcerne = livreService.getLivreById(id);
        newAttentereservation.setTitreLivre(livreConcerne.getTitre());
        AttenteReservationDTO attenteReservation =attenteReservationService.createAttenteReservation(newAttentereservation);
        userService.verifierUserConnecte(model);
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("livre",livreConcerne);
        model.addAttribute("attenteReservation",attenteReservation);
        return "attenteReservation/demandeAttenteReservation";
    }

}
