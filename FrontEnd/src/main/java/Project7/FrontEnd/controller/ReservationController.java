package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.service.AuthService;
import Project7.FrontEnd.service.LivreService;
import Project7.FrontEnd.service.ReservationService;
import Project7.FrontEnd.service.UserService;
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
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    public ReservationService reservationService;

    @Autowired
    public UserService userService;

    @Autowired
    public LivreService livreService;

    @Autowired
    public AuthService authService;

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationController.class);

    /* controller pour avoir toutes les reservations*/
    @RequestMapping(value="/all",method = RequestMethod.GET)
    public String getAllReservations(Model model, Principal principal) throws IOException, InterruptedException {
        List<ReservationDTO> listeReservations = reservationService.getAllReservations();
        if(authService.getUserConnecte()!=null){
            model.addAttribute("role",authService.getUserConnecte().getRole().getNomRole());
        }else{
            model.addAttribute("role",null);
        }
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("reservations",listeReservations);
        return "reservation/listeReservations";
    }

    /* controller pour envoyer une réservation d'un livre pour l'API*/
    @RequestMapping(value="/reserver/livre/{id}",method = RequestMethod.POST)
    public String reservationLivre(Model model,Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {

        //UserDTO userConnecte = userService.getUserById(1);
        UserDTO userConnecte =authService.getUserConnecte();
        ReservationDTO reservation = new ReservationDTO();
        reservation.setUser(userConnecte);
        LivreDTO livreReserve = livreService.getLivreById(id);
        livreReserve.setDisponibilite(false);
        reservation.setLivre(livreReserve);
        ReservationDTO reservation2 =reservationService.createReservation(reservation);
        LivreDTO livre=livreService.modifierUnLivre(livreReserve);
        userService.verifierUserConnecte(model);
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("livre",livre);
        model.addAttribute("reservation",reservation2);
        return "reservation/demandeReservation";
    }

    /* controller pour avoir le détail de la réservation */
    @RequestMapping(path="/detail/{id}",method = RequestMethod.GET)
    public String getDetailsReservation(Model model,Principal principal, @PathVariable("id") int id) throws IOException, ParseException, InterruptedException {
        ReservationDTO reservationDetail = reservationService.getReservationById(id);
        userService.verifierUserConnecte(model);
        model.addAttribute("isAuthentified",authService.getAuthentification());
        model.addAttribute("reservation",reservationDetail);
        model.addAttribute("livre",reservationDetail.getLivre());
        return "reservation/reservationDetail"; //view
    }


    /* controller pour le retrait du livre à la bibliiothèque */
    @RequestMapping(value="/retirer/{id}",method = RequestMethod.POST)
    public String reservationRetirer(Model model,Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {
        ReservationDTO reservationARetirer =reservationService.getReservationById(id);
        ReservationDTO reservationRetiree = reservationService.retirerReservation(reservationARetirer);
        //model.addAttribute("reservation",reservationRetiree);
        return "redirect:/user/EspaceAdministration";
    }


    /* controller pour le retour du livre à la bibliiothèque */
    @RequestMapping(value="/retourDeLocation/{id}",method = RequestMethod.POST)
    public String reservationRetourner(Model model,Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {
        ReservationDTO reservationARetourner =reservationService.getReservationById(id);
        ReservationDTO reservationRetiree = reservationService.retournerReservation(reservationARetourner);
        // rajout de la méthode pour reserver le livre pour le user en attente
        return "redirect:/user/EspaceAdministration";
    }


    /* controller pour prolonger la location limité à une fois si le délai n'est pas dépassé */
    @RequestMapping(value="/prolonger/{id}",method = RequestMethod.POST)
    public String reservationProlonger(Model model,Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {
        ReservationDTO reservationAProlonger =reservationService.getReservationById(id);
        reservationService.verifierReservation(reservationAProlonger);
        if (!reservationAProlonger.getProlongation()){
            ReservationDTO reservationProlonger = reservationService.prolongerReservation(reservationAProlonger);
        }
        //model.addAttribute("reservation",reservationRetiree);
        return "redirect:/user/EspacePersonnel";
    }

}
