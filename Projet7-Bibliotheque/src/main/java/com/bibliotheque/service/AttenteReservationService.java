package com.bibliotheque.service;

import com.bibliotheque.entity.User;
import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.repository.AttenteReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttenteReservationService {

    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationService.class);

    @Autowired
    AttenteReservationRepository attenteReservationRepository;


    /*Methode pour avoir toutes les attentes de reservation actives de la base de données*/
    public List<AttenteReservation> findAllAttenteReservation() {
        return attenteReservationRepository.findAllByIsactifAttente(true);
    }

    /*Methode pour avoir toutes les attentes de reservation actives par user de la base de données*/
    public List<AttenteReservation> findAllAttenteReservationByUser(User user) {
        return attenteReservationRepository.findAllByUserAndIAndIsactifAttente(user,true);
    }


}
