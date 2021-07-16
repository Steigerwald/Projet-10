package com.projet7.BibliothequeVille.repository;

import com.projet7.BibliothequeVille.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository <Reservation,Long> {
}
