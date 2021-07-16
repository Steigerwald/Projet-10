package com.projet7.BibliothequeVille.service;

import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.Reservation;
import com.projet7.BibliothequeVille.entity.User;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationService.class);


    @Autowired
    public ReservationRepository reservationRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public LivreService livreService;

    /*Methode pour obtenir toutes les réservations de la base de données*/
    public List<Reservation> getAllReservations() {
        List<Reservation> result1 =(List<Reservation>) reservationRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 de toutes les reservations de la BD avec getAllReservations si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Reservation>();
        }
    }

    /*Methode pour obtenir une reservation par Id de la base de données*/
    public Reservation getReservationById(Long id) throws RecordNotFoundException {
        Optional<Reservation> reservationTrouve = reservationRepository.findById(id);
        if(reservationTrouve.isPresent()) {
            logger.info(" retour de la reservation car elle est présente ");
            return reservationTrouve.get();
        } else {
            throw new RecordNotFoundException("Pas de réservation enregistrée avec cet Id");
        }
    }

    /*Methode pour creer une réservation dans la base de données*/
    public Reservation CreateReservation(Reservation entity) throws RecordNotFoundException {
        Date today = new Date();
        Reservation newReservation = new Reservation();
        newReservation.setEtatReservation("En attente confirmation");
        newReservation.setDateReservation(today);
        newReservation.setDateDeRetrait(null);
        newReservation.setDelaiDeLocation(15);
        newReservation.setIsactif(true);
        newReservation.setUser(entity.getUser());
        newReservation.setLivres(entity.getLivres());
        if (entity.getLivres().size()>0){
            for (Livre livre:entity.getLivres()){
               livre.setReservation(newReservation);
                logger.info(" passage du for de entity.getLivres");
                livreService.updateLivre(livre);
            }
        }

        //enregistrement de la réservation dans la basse de données
        entity = reservationRepository.save(newReservation);
        logger.info(" retour de l'entité newResevation de createReservation qui a été créée et sauvegardée");
        return entity;
    }

    /*Methode pour modifier une réservation dans la base de données*/
    public Reservation UpdateReservation(Reservation entity) throws RecordNotFoundException {
        Reservation reservationAModifier = getReservationById(entity.getIdReservation());
        if (reservationAModifier != null) {
            logger.info(" l'entité reservation à modifier a été trouvée et peut être modifiée");

            reservationAModifier.setEtatReservation(entity.getEtatReservation());
            reservationAModifier.setDateReservation(entity.getDateReservation());
            reservationAModifier.setDateDeRetrait(entity.getDateDeRetrait());
            reservationAModifier.setDelaiDeLocation(entity.getDelaiDeLocation());
            reservationAModifier.setIsactif(entity.getIsactif());

            reservationAModifier = reservationRepository.save(reservationAModifier);
            logger.info(" retour de la nouvelle entité site de UpdateReservation qui a été sauvegardée et la reservationAModifier était existante");
            return reservationAModifier;
        } else {
            throw new RecordNotFoundException("Pas de reservation trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }

    /*Methode pour effacer une réservation dans la base de données*/
    public void deleteReservationById(Long id) throws RecordNotFoundException {
        Optional<Reservation> reservationAEffacer = reservationRepository.findById(id);
        if(reservationAEffacer.isPresent()) {
            Reservation reservationTrouve = reservationAEffacer.get();
            User userConcerne = reservationTrouve.getUser();
            userConcerne.getReservations().remove(reservationTrouve);
            userConcerne.setReservations(userConcerne.getReservations());
            userService.updateUser(userConcerne);
            reservationTrouve.setUser(null);

            Collection<Livre> livresConcernes = reservationTrouve.getLivres();
            assert livresConcernes != null;
            for (Livre livre:livresConcernes)
                  {livre.setReservation(null);
                  livreService.updateLivre(livre);
            }
            reservationTrouve.setLivres(null);
            UpdateReservation(reservationTrouve);
            reservationRepository.deleteById(reservationTrouve.getIdReservation());
        } else {
            throw new RecordNotFoundException("Pas de reservation enregistrée avec cet Id");
        }
    }

    /*Methode pour annuler une réservation */
    public Reservation annulerReservation(Reservation entity) throws RecordNotFoundException {
        Reservation reservationAModifier = getReservationById(entity.getIdReservation());
        if (reservationAModifier != null) {
            logger.info(" l'entité reservation à modifier a été trouvée et peut être modifiée");
            reservationAModifier.setIsactif(false);

            reservationAModifier = reservationRepository.save(reservationAModifier);
            logger.info(" retour de la nouvelle entité site de UpdateReservation qui a été sauvegardée et la reservationAModifier était existante");
            return reservationAModifier;
        } else {
            throw new RecordNotFoundException("Pas de reservation trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }

}
