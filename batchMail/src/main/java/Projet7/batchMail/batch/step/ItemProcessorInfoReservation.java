package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.EmailService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ItemProcessorInfoReservation implements ItemProcessor<ReservationDTO,ReservationDTO>  {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmailService emailService;

    /* Step pour le mail de relance pour la location lorsque la date est dépassée*/
    @Override
    public ReservationDTO process(ReservationDTO s) throws Exception {
        System.out.println("ceci est le processor de " + s.getUser().getNomUser());
        System.out.println("le mail est: " + s.getUser().getMailUser());
        Date today = new Date();
        s.setDateMailInfo(today);
        Date dateLimite = new Date(today.getTime()+2*(1000*60*60*24));
        emailService.sendSimpleMessage(s.getUser().getMailUser(),"Vous pouvez désormais retirer votre livre "+s.getLivre().getTitre(),"à la bibliothèque "+s.getLivre().getBibliotheque().getNomBibliotheque()+ " et vous devez le retirer avant le "+dateLimite);
        return s;
    }
}
