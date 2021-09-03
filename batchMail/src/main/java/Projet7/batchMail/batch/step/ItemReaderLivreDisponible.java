package Projet7.batchMail.batch.step;

import Projet7.batchMail.dto.LivreDTO;
import Projet7.batchMail.dto.ReservationDTO;
import Projet7.batchMail.service.AttenteReservationService;
import Projet7.batchMail.service.LivreService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Component
public class ItemReaderLivreDisponible implements ItemReader<LivreDTO> {

    @Autowired
    LivreService livreService;

    @Autowired
    AttenteReservationService attenteReservationService;

    @Override
    public LivreDTO read() throws IOException, InterruptedException, ParseException {
        livreService.setItemLivresDisponibles(livreService.getAllLivresDisponibles());
        return null;
    }
}