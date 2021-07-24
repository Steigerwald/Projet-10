package com.bibliotheque.service;

import com.bibliotheque.entity.Livre;
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
        return attenteReservationRepository.findAllByUserAndIsactifAttente(user,true);
    }

    /*Methode pour avoir toutes les attentes de reservation actives par livre de la base de données*/
    public List<AttenteReservation> findAllAttenteReservationByLivre(Livre livre) {
        return attenteReservationRepository.findAllByLivreAndIsactifAttente(livre,true);
    }

    /*Methode pour trouver par son id une attente de reservation dans la base de données*/

    /*Methode pour sauvegarder une attente de reservation dans la base de données*/

    /*Methode pour creer une attente de reservation dans la base de données*/

    /*Methode pour effacer une attente de réservation dans la base de données*/

    /*Methode pour annuler une attente de réservation */

    /*Methode pour vérifier la date de retrait et la date de disponibilité < 48H*/

    /*Methode pour modifier l'etat de l'attente en fonction la validité de la date de retrait d'un livre et de la disponibilté*/


}
