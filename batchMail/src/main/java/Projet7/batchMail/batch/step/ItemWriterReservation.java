package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.ReservationDTO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemWriterReservation implements ItemWriter<ReservationDTO> {

    @Override
    public void write(List<? extends ReservationDTO> list) throws Exception {

    }
}
