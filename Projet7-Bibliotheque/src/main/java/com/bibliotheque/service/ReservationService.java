package com.bibliotheque.service;

import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.User;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    AttenteReservationService attenteReservationService;

    /*Methode pour avoir toutes les reservations actives de la base de données*/
    public List<Reservation> findAll() {
        //return reservationRepository.findAllAndIsactif();
        return reservationRepository.findAll();
    }

    /*Methode pour trouver par son id une reservation dans la base de données*/
    public Reservation findById(int id) {
        return  reservationRepository.findById(id).get();
    }

    /*Methode pour sauvegarder une reservation dans la base de données*/
    public void save(Reservation reservation) {
        logger.info(" l'id de reservation vaut: "+reservation.getIdReservation());
        reservationRepository.save(reservation);
    }


    /*Methode pour creer une reservation dans la base de données*/
    public Reservation createReservation(Reservation entity) throws RecordNotFoundException {
        if (entity.getLivre()!=null){
            if (entity.getLivre().getDisponibilite()){
                Date today = new Date();
                Reservation newReservation = new Reservation();
                entity.setIdReservation(newReservation.getIdReservation());
                entity.setDateDeRetrait(null);
                entity.setDateReservation(today);
                entity.setDelaiDeLocation(28);
                entity.setEtatReservation("En attente confirmation");
                entity.setProlongation(false);
                entity.setIsactif(true);
                entity.setRelance(false);
                //enregistrement de la reservation dans la basse de données
                logger.info(" retour de l'entité newReservation de createBReservation qui a été créée et sauvegardée");
                return reservationRepository.save(entity);
            }else{
               attenteReservationService.createAttenteReservation(entity.getUser(),entity.getLivre());
            }
        }
        return entity;
    }

    /*Methode pour modifier une réservation dans la base de données*/
    public Reservation updateReservation(Reservation entity) throws RecordNotFoundException {
        Reservation reservationAModifier = findById((entity.getIdReservation()));
        if (reservationAModifier != null) {
            logger.info(" l'entité reservation à modifier a été trouvée et peut être modifiée");
            //enregistrement de la reservation dans la basse de données
            logger.info(" retour de la nouvelle entité site de UpdateReservation qui a été sauvegardée et la reservationAModifier était existante");
            return reservationRepository.save(entity);
        } else {
            throw new RecordNotFoundException("Pas de reservation trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }

    /*Methode pour effacer une réservation dans la base de données*/
    public void deleteReservationById(int id) throws RecordNotFoundException {
        Optional<Reservation> reservationAEffacer = reservationRepository.findById(id);
        if(reservationAEffacer.isPresent()) {
            Reservation reservationTrouve = reservationAEffacer.get();
            reservationRepository.deleteById(reservationTrouve.getIdReservation());
        } else {
            throw new RecordNotFoundException("Pas de reservation enregistrée avec cet Id");
        }
    }

    /*Methode pour annuler une réservation */
    public Reservation annulerReservation(Reservation entity) throws RecordNotFoundException {
        Reservation reservationAModifier = findById((entity.getIdReservation()));
        if (reservationAModifier != null) {
            logger.info(" l'entité reservation à modifier a été trouvée et peut être modifiée");
            reservationAModifier.setIsactif(false);
            logger.info(" retour de la nouvelle entité site de UpdateReservation qui a été sauvegardée et la reservationAModifier était existante");
            return reservationRepository.save(reservationAModifier);
        } else {
            throw new RecordNotFoundException("Pas de reservation trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }

    /*Methode pour avoir toutes les reservations à valider de la base de données*/
    public List<Reservation> findAllAValider() {
        String etatEnCours = "En attente confirmation";
        return reservationRepository.findAllByEtatReservation(etatEnCours);
    }

    /*Methode pour avoir toutes les reservations en cours de la base de données*/
    public List<Reservation> findAllEnCours() {
        String etatEnCours = "En cours de pret";
        return reservationRepository.findAllByEtatReservationOrIsactif(etatEnCours,true);
    }

    /*Methode pour avoir toutes les reservations d'un user en cours de la base de données*/
    public List<Reservation> findAllByUser(User user) {
        return reservationRepository.findAllByUser(user);
    }


    /*Methode pour vérifier la validité de la date de location d'un livre*/
    public boolean verifierDateDeRetrait (Reservation entity){
        Date today = new Date();
        int delaiDeRetour =entity.getDelaiDeLocation();
        if (entity.getProlongation()){
            delaiDeRetour = delaiDeRetour*2;
        }
        boolean result=true;
        if (entity.getDateDeRetrait()!=null) {
            Date diff = new Date(today.getTime() - entity.getDateDeRetrait().getTime());
            int nombreJours = (int)(diff.getTime()/1000/3600/24);
            logger.info("nombreJours de la différence: "+nombreJours);
            if (nombreJours > entity.getDelaiDeLocation()){
                result=false;
            } else{
                result=true;
            }
        }
        return result;
    }

    /*Methode pour modifier l'etat de la commande en fonction la validité de la date de location d'un livre*/
    public Reservation verifierEtatReservation (Reservation entity){
        if (!(verifierDateDeRetrait(entity))){
            entity.setEtatReservation("delai depasse");
            entity.setProlongation(true);
            reservationRepository.save(entity);
        }
        return entity;
    }

}
