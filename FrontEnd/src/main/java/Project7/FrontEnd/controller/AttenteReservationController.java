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
    public ReservationService reservationService;

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
        attenteReservationService.annulerAttenteReservation(attenteReservationAAnnuler.getIdAttenteReservation());
        //model.addAttribute("reservation",reservationRetiree);
        return "redirect:/user/EspacePersonnel";
    }


    /* controller pour creer une attente de réservation d'un livre pour l'API*/
    @RequestMapping(value="/creerAttente/livre/{id}",method = RequestMethod.POST)
    public String reservationLivre(Model model,Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {
        logger.info(" on est passé par là creerAttente/livre/  "+id);
        UserDTO userConnecte =authService.getUserConnecte();
        logger.info(" userConnecte est  "+userConnecte);
        LivreDTO livreConcerne = livreService.getLivreById(id);
        logger.info(" le livreConcerné est  "+livreConcerne.getTitre());
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("livre",livreConcerne);
        logger.info(" on est passé par là creerAttente/livre/  suite1 "+id);
        Boolean userDansListeAttente =attenteReservationService.getVerificationUserIsPresentInListeAttente(id,userConnecte.getIdUser());
        Boolean listeAttenteSuffisante = attenteReservationService.getVerificationListeAttenteSuffisante(livreConcerne.getIdLivre());
        ReservationDTO newReservation = new ReservationDTO();
        newReservation.setUser(userConnecte);
        newReservation.setLivre(livreConcerne);
        Boolean userPossedeDejaLivre = reservationService.verifierPossessionLivre(newReservation);
        logger.info(" on est passé par là creerAttente/livre/  suite2 "+id);
        logger.info(" valeur boolean userPossedeDejaLivre "+userPossedeDejaLivre);
        logger.info(" valeur de userDansListeAttente "+userDansListeAttente);
        logger.info(" valeur de listeAttenteSuffisante "+listeAttenteSuffisante);
        if ((!userDansListeAttente)&&(listeAttenteSuffisante)&&(!userPossedeDejaLivre)){
            logger.info(" on est passé par là creerAttente/livre/  suite3 "+id);
        AttenteReservationDTO newAttenteReservation = new AttenteReservationDTO();
        newAttenteReservation.setUser(userConnecte);
        newAttenteReservation.setTitreLivre(livreConcerne.getTitre());
            logger.info(" on est passé par là creerAttente/livre/  suite3bis "+livreConcerne.getTitre());
        AttenteReservationDTO attenteReservation =attenteReservationService.createAttenteReservation(newAttenteReservation);
            logger.info(" valeur de attenteReservation"+attenteReservation);
        userService.verifierUserConnecte(model);
        model.addAttribute("attenteReservation",attenteReservation);
            logger.info(" OK on est dans demandeAttenteReservation #############");
        return "attenteReservation/demandeAttenteReservation";
        }else{
            if(userDansListeAttente){
                logger.info(" on est passé par là creerAttente/livre/  suite4 "+id);
                AttenteReservationDTO attenteReservationTrouvee =attenteReservationService.getAttenteReservationByIdLivreAndByIdUser(livreConcerne.getIdLivre(),userConnecte.getIdUser());
                logger.info(" refusée on est dans demandeAttenteReservationRefusee #############");
                model.addAttribute("attenteReservation",attenteReservationTrouvee);
                return "attenteReservation/demandeAttenteReservationRefusee";
            }else{
                logger.info(" on est passé par là creerAttente/livre/  suite5 "+id);
                logger.info(" refusée on est dans demandeAttenteReservationRefusee à cause d'une liste d'attente insuffisante#############");
                return "attenteReservation/demandeAttenteReservationRefuseeListeInsuffisante";
            }
        }
    }
}
