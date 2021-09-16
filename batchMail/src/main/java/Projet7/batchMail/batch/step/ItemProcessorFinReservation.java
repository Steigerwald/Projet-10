package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.EmailService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ItemProcessorFinReservation implements ItemProcessor<ReservationDTO,ReservationDTO> {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReservationService reservationService;

    /* Step pour le mail de relance pour la location lorsque la date est dépassée*/
    @Override
    public ReservationDTO process(ReservationDTO s) throws Exception {
        System.out.println("ceci est le processor de " + s.getUser().getNomUser());
        System.out.println("le mail est: " + s.getUser().getMailUser());
        Date today = new Date();
        Date dateLimite = new Date(s.getInfo().getTime()+2*(1000*60*60*24));
        SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd-MM-yyyy");
        if(today.after(dateLimite)){
            s.setIsactif(false);
            emailService.sendSimpleMessage(s.getUser().getMailUser(),"annulation de votre location de livre","Vous n'avez pas retirer votre livre "+s.getLivre().getTitre()+" à la bibliothèque "+s.getLivre().getBibliotheque().getNomBibliotheque()+ " avant le "+simpleDateFormat02.format(dateLimite)+" votre résevation est annulée");
        }
        return s;
    }
}
