package com.bibliotheque.service;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.User;
import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.repository.AttenteReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AttenteReservationService {

    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationService.class);

    @Autowired
    AttenteReservationRepository attenteReservationRepository;

    @Autowired
    LivreService livreService;

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

    /*Methode pour trouver par son id une reservation dans la base de données*/
    public AttenteReservation findAttenteReservationById(int id) {
        return  attenteReservationRepository.findById(id).get();
    }


    /*Methode pour creer une attente de reservation pour un livre de la part d'un user dans la base de données*/
    public AttenteReservation createAttenteReservation(User user,Livre livre) throws RecordNotFoundException {

        int nombreExemplaire=(livreService.getAllExemplairesDUnLivre(livre.getIdLivre()).size());
        List<Livre> listeDExemplairesDisponibles=livreService.getAllExemplairesDidponiblesDUnLivre(livre.getIdLivre());
        List<AttenteReservation> listeDAttente = new ArrayList<AttenteReservation>();
        listeDAttente=findAllAttenteReservationByLivre(livre);
        AttenteReservation entity = new AttenteReservation();

        if ((listeDAttente.size()<nombreExemplaire*2) && (nombreExemplaire!=0)){
            if (listeDExemplairesDisponibles.size()>0){
                System.out.println("le livre est disponible, vous pouvez le réserver");
            }else{
                Date today = new Date();
                entity.setDateAttenteReservation(today);
                entity.setEtatAttenteReservation("Sur liste d'attente");
                entity.setDatedelaiDepasse(false);
                entity.setIsactifAttente(true);
                entity.setUser(user);
                //enregistrement de la reservation dans la basse de données
                logger.info(" retour de l'entité newAttenteReservation de createAttenteReservation qui a été créée et sauvegardée");
                attenteReservationRepository.save(entity);
            }
        }else{
            System.out.println("la liste d'attente est complète ou il n'y a pas ce livre dans la base");
        }
        return entity;
    }


    /*Methode pour effacer une attente de réservation dans la base de données*/

    /*Methode pour annuler une attente de réservation */

    /*Methode pour vérifier la date de retrait et la date de disponibilité < 48H*/

    /*Methode pour modifier l'etat de l'attente en fonction la validité de la date de retrait d'un livre et de la disponibilté*/


}
