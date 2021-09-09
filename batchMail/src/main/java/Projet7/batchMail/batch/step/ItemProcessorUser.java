package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.EmailService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessorUser implements ItemProcessor<ReservationDTO,ReservationDTO> {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmailService emailService;

    /* Step pour le mail de relance pour la location lorsque la date est dépassée*/
    @Override
    public ReservationDTO process(ReservationDTO s) throws Exception {
            System.out.println("ceci est le processor de " + s.getUser().getNomUser());
            System.out.println("le mail est: " + s.getUser().getMailUser());
            emailService.sendSimpleMessage(s.getUser().getMailUser(),"relance pour votre location de livre","votre date de location du livre "+s.getLivre().getTitre()+" est dépassé, merci de vous rapprocher de votre librairie pour retourner votre livre ou prolonger votre location !");
            //reservationService.getReservationsRelancees().add(s);
            return s;
    }





}
