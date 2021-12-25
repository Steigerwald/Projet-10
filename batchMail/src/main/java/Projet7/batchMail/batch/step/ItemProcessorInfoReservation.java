package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.EmailService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ItemProcessorInfoReservation implements ItemProcessor<ReservationDTO,ReservationDTO>  {

    @Autowired
    private EmailService emailService;

    /* Step pour le mail de relance pour la location lorsque la date est dépassée*/
    @Override
    public ReservationDTO process(ReservationDTO s) throws Exception {
        System.out.println("ceci est le processor de itemPRocessorInfoReservation de " + s.getUser().getNomUser());
        System.out.println("le mail est: " + s.getUser().getMailUser());
        Date today = new Date();
        s.setInfo(today);
        // date limite à ne pas dépasser d'une minute
        Date dateLimite = new Date(s.getInfo().getTime()+(1000*60));
        //Date dateLimite = new Date(today.getTime()+2*(1000*60*60*24));
        SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd-MM-yyyy");
        emailService.sendSimpleMessage(s.getUser().getMailUser(),"confirmation de votre location de livre","Vous pouvez désormais retirer votre livre "+s.getLivre().getTitre()+" à la bibliothèque "+s.getLivre().getBibliotheque().getNomBibliotheque()+ " et vous devez le retirer avant le "+simpleDateFormat02.format(dateLimite));
        return s;
    }
}
