package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.AttenteReservationDTO;
import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.dto.UserDTO;
import Projet7.batchMail.service.AttenteReservationService;
import Projet7.batchMail.service.LivreService;
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
    private LivreService livreService;

    @Autowired
    private AttenteReservationService attenteReservationService;

    @Override
    public void write(List<? extends ReservationDTO> list) throws Exception {
        for (int i=0; i<list.size(); i=i+1){
            //reservationService.modifyReservationBatch(list.get(i));
            Date today = new Date();
            Date dateLimite = new Date(list.get(i).getInfo().getTime()+2*(1000*60));
            //Date dateLimite = new Date(list.get(i).getInfo().getTime()+2*(1000*60*60*24));
            if (today.after(dateLimite)) {
                list.get(i).setEtatReservation("jamais retiree");
                list.get(i).setIsactif(false);
                List<UserDTO> listeUsersEnAttente=attenteReservationService.getAllAttenteUsersByIdLivre(list.get(i).getLivre().getIdLivre());
                //reservationService.modifyReservationBatch(list.get(i));
                if (listeUsersEnAttente.size()>0){
                    list.get(i).getLivre().setDisponibilite(false);
                    ReservationDTO newReservation=new ReservationDTO();
                    newReservation.setIsactif(true);
                    newReservation.setUser(listeUsersEnAttente.get(0));
                    newReservation.setLivre(list.get(i).getLivre());
                    ReservationDTO nouvelleReservation =reservationService.createReservation(newReservation);
                    AttenteReservationDTO attenteAAnnuler = attenteReservationService.getAttenteReservationByIdLivreAndByIdUser(list.get(i).getLivre().getIdLivre(),listeUsersEnAttente.get(0).idUser);
                    attenteReservationService.annulerAttenteReservation(attenteAAnnuler.getIdAttenteReservation());
                    System.out.println("l'attente réservation N° " + attenteAAnnuler.getIdAttenteReservation() + " a été annulée");
                    System.out.println("la nouvelle réservation N° " + nouvelleReservation.getIdReservation() + " a été créée dans la base");

                }else{
                    list.get(i).getLivre().setDisponibilite(true);
                }
                reservationService.modifyReservationBatch(list.get(i));
                livreService.modifierUnLivre(list.get(i).getLivre());
            }
            System.out.println("la réservation N° " + list.get(i).getIdReservation() + " a été prévenue");
        }
        System.out.println("l'enregistrement de ItemWriterInfoReservation est terminé");
    }
}
