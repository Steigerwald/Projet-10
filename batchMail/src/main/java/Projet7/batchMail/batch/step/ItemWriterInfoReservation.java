package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemWriterInfoReservation implements ItemWriter<ReservationDTO> {

    @Autowired
    private ReservationService reservationService;

    @Override
    public void write(List<? extends ReservationDTO> list) throws Exception {
        for (int i=0; i<list.size(); i=i+1){
            reservationService.modifyReservationBatch(list.get(i));
            System.out.println("la réservation N° " + list.get(i).getIdReservation() + " a été prévenue");
        }
        System.out.println("l'enregistrement de ItemWriterInfoReservation est terminé");
    }
}