package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.LivreDTO;
import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.LivreService;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
public class ItemReaderInfoReservation implements ItemReader<ReservationDTO> {

    @Autowired
    LivreService livreService;

    @Autowired
    ReservationService reservationService;

    int i =0;

    @Override
    public ReservationDTO read () throws IOException, InterruptedException {

        reservationService.setItemInfoReservations(reservationService.getAllReservationsEnAttenteInfoMail());

        if (i==reservationService.getItemReservations().size()){
            i=0;
            return null;
        }
        ReservationDTO reservationDTO =reservationService.getItemReservations().get(i);
        System.out.println(reservationDTO);
        System.out.println(i);

        i++;
        return reservationDTO;
    }








}
