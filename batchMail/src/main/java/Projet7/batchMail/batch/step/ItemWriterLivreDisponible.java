package Projet7.batchMail.batch.step;


import Projet7.batchMail.dto.AttenteReservationDTO;
import Projet7.batchMail.dto.LivreDTO;
import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.dto.UserDTO;
import Projet7.batchMail.service.AttenteReservationService;
import Projet7.batchMail.service.LivreService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemWriterLivreDisponible implements ItemWriter<ReservationDTO> {

    @Autowired
    AttenteReservationService attenteReservationService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    LivreService livreService;

    @Override
    public void write(List<? extends ReservationDTO> list) throws Exception {
        for (int i=0; i<list.size(); i=i+1){
            if (list.get(i).getUser() != null){
                //reservationService.createReservation(list.get(i));
                LivreDTO livreConcerne=list.get(i).getLivre();
                UserDTO userConcerne = list.get(i).getUser();
                AttenteReservationDTO attenteReservationDTO =attenteReservationService.getAttenteReservationByIdLivreAndByIdUser(livreConcerne.getIdLivre(),userConcerne.idUser);
                attenteReservationService.annulerAttenteReservation(attenteReservationDTO);
                list.get(i).getLivre().setDisponibilite(false);
                livreService.modifierUnLivre(list.get(i).getLivre());
                System.out.println("la réservation N° " + list.get(i).getIdReservation() + " a été crée");
            }else{
                list.get(i).getLivre().setDisponibilite(true);
                livreService.modifierUnLivre(list.get(i).getLivre());
            }
        }
        System.out.println("l'enregistrement de ItemWriterLivreDisponible est terminé");
    }
}
