package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.ReservationService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ItemReaderUser implements ItemReader<ReservationDTO> {

    @Autowired
    private ReservationService reservationService;

    int i =0;

    @Override
    public ReservationDTO read () throws IOException, InterruptedException {

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
