package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.AuthService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class ItemReaderReservation implements ItemReader<ReservationDTO> {

    @Autowired
    public ReservationService reservationService;

    @Autowired
    public AuthService authService;

    @Override
    public ReservationDTO read () throws IOException, InterruptedException {
        reservationService.setItemReservations(reservationService.getAllReservationsBatch());
        return null;
    }
}
