package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.AttenteReservationDTO;
import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.dto.UserDTO;
import Projet7.batchMail.service.AttenteReservationService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ItemWriterFinReservation implements ItemWriter<ReservationDTO> {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AttenteReservationService attenteReservationService;

    @Override
    public void write(List<? extends ReservationDTO> list) throws Exception {
        for (int i=0; i<list.size(); i=i+1){
            reservationService.modifyReservationBatch(list.get(i));
            Date today = new Date();
            Date dateLimite = new Date(list.get(i).getInfo().getTime()+2*(1000*60));
            //Date dateLimite = new Date(list.get(i).getInfo().getTime()+2*(1000*60*60*24));
            if (today.after(dateLimite)) {
                reservationService.effacerUneReservation(list.get(i));
                List<UserDTO> listeUsersEnAttente=attenteReservationService.getAllAttenteUsersByIdLivre(list.get(i).getLivre().getIdLivre());
                //reservationService.modifyReservationBatch(list.get(i));
                if (listeUsersEnAttente.size()>0){
                    ReservationDTO newReservation=new ReservationDTO();
                    newReservation.setUser(listeUsersEnAttente.get(0));
                    newReservation.setLivre(list.get(i).getLivre());
                    AttenteReservationDTO attenteAAnnuler = attenteReservationService.getAttenteReservationByIdLivreAndByIdUser(listeUsersEnAttente.get(0).idUser, list.get(i).getLivre().getIdLivre());
                    attenteReservationService.annulerAttenteReservation(attenteAAnnuler);
                    ReservationDTO nouvelleReservation =reservationService.createReservation(newReservation);
                    System.out.println("l'attente réservation N° " + attenteAAnnuler.getIdAttenteReservation() + " a été annulée");
                    System.out.println("la nouvelle réservation N° " + nouvelleReservation.getIdReservation() + " a été créée dans la base");
                }
            }
            System.out.println("la réservation N° " + list.get(i).getIdReservation() + " a été prévenue");
        }
        System.out.println("l'enregistrement de ItemWriterInfoReservation est terminé");
    }
}
