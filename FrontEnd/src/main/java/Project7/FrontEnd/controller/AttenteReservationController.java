package Project7.FrontEnd.controller;

import Project7.FrontEnd.dto.AttenteReservationDTO;
import Project7.FrontEnd.dto.ReservationDTO;
import Project7.FrontEnd.service.AttenteReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;

@Controller
@RequestMapping("/attenteReservation")
public class AttenteReservationController {

    @Autowired
    AttenteReservationService attenteReservationService;

    @RequestMapping(value="/annuler/{id}",method = RequestMethod.POST)
    public String attenteReservationAnnuler(Model model, Principal principal, @PathVariable("id") int id) throws IOException, InterruptedException, ParseException {
        AttenteReservationDTO attenteReservationAAnnuler =attenteReservationService.getAttenteReservationById(id);
        AttenteReservationDTO attenteReservationAnnulee = attenteReservationService.annulerAttenteReservation(attenteReservationAAnnuler);
        //model.addAttribute("reservation",reservationRetiree);
        return "redirect:/user/EspacePersonnel";
    }
}
