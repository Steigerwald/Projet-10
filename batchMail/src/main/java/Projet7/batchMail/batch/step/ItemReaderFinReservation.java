package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ItemReaderFinReservation implements ItemReader<ReservationDTO> {

    @Autowired
    ReservationService reservationService;

    int i =0;

    @Override
    public ReservationDTO read () throws IOException, InterruptedException {

        reservationService.setItemFinReservations(reservationService.getAllReservationsEnAttenteDateRetraitNull());

        if (i==reservationService.getItemFinReservations().size()){
            i=0;
            return null;
        }
        ReservationDTO reservationDTO =reservationService.getItemFinReservations().get(i);
        System.out.println(reservationDTO);
        System.out.println(i);

        i++;
        return reservationDTO;
    }
}
