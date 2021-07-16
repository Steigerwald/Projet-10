package com.bibliotheque.repository;

import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByEtatReservation(String etatReservation);
    List<Reservation> findAllByEtatReservationOrIsactif(String etatReservation,Boolean actif);
    List<Reservation> findAllByUser(User user);
}
