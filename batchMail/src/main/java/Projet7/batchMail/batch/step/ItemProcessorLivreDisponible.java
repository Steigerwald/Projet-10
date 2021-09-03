package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.AttenteReservationDTO;
import Projet7.batchMail.dto.LivreDTO;
import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.dto.UserDTO;
import Projet7.batchMail.service.AttenteReservationService;
import Projet7.batchMail.service.EmailService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessorLivreDisponible implements ItemProcessor<LivreDTO,ReservationDTO> {

    @Autowired
    AttenteReservationService attenteReservationService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    private EmailService emailService;

    @Override
    public ReservationDTO process(LivreDTO livreDTO) throws Exception {

        attenteReservationService.setItemUsersAttente(attenteReservationService.getAllAttenteUsersByIdLivre(livreDTO.getIdLivre()));
        ReservationDTO newReservation = new ReservationDTO();
        if (attenteReservationService.getItemUsersAttente().size()>0){
            UserDTO userAttente = attenteReservationService.getItemUsersAttente().get(0);
            newReservation.setLivre(livreDTO);
            newReservation.setUser(userAttente);
            System.out.println("ceci est le processor de " + userAttente.getNomUser());
            System.out.println("le mail est: " + userAttente.getMailUser());
            emailService.sendSimpleMessage(userAttente.getMailUser(),"Confirmation de location du livre","votre livre "+livreDTO.getTitre()+" est désormais disponible, merci de vous rapporocher de votre librairie pour retirer votre livre avant 48h !");
            reservationService.createReservation(newReservation);

        } else{
            newReservation.setLivre(livreDTO);
            newReservation.setUser(null);
        }
        System.out.println(livreDTO);
        return newReservation;
    }
}
