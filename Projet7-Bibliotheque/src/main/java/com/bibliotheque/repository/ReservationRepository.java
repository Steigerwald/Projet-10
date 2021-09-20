package com.bibliotheque.repository;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByEtatReservation(String etatReservation);
    List<Reservation> findAllByEtatReservationAndIsactif(String etatReservation,Boolean actif);
    List<Reservation> findAllByEtatReservationOrEtatReservationAndIsactif(String etat1,String etat2,Boolean actif);
    List<Reservation> findAllByUser(User user);
    Collection<Reservation> findAllByUserAndLivre(User user, Livre livre);
    //Reservation findByUserAndLivre(User user, Livre livre);
    List<Reservation> findAllByEtatReservationAndInfoIsNull(String etatReservation);
    List<Reservation> findAllByEtatReservationAndDateDeRetraitIsNull(String etatReservation);
    List<Reservation> findAllByLivreAndIsactif(Livre livre,Boolean actif);
    List<Reservation> findAllByLivreAndDateDeRetraitIsNotNullAndIsactif(Livre livre,Boolean actif);
}
