package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessorReservation implements ItemProcessor<ReservationDTO,ReservationDTO> {

    @Override
    public ReservationDTO process(ReservationDTO s) throws Exception {
        System.out.println(s);
        return s;
    }
}
