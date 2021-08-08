package com.bibliotheque.repository;

import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AttenteReservationRepository extends JpaRepository<AttenteReservation, Integer> {
    List<AttenteReservation> findAllByEtatAttenteReservation(String etatAttenteReservation);
    List<AttenteReservation> findAllByEtatAttenteReservationOrIsactifAttente(String etatAttenteReservation,Boolean actifAttente);
    List<AttenteReservation> findAllByUser(User user);
    List<AttenteReservation> findAllByUserAndIsactifAttente(User user,Boolean actifAttente);
    List<AttenteReservation> findAllByIsactifAttente(Boolean actifAttente);
    //List<AttenteReservation> findAllByLivreAndIsactifAttente(Livre livre, Boolean actifAttente);
    List<AttenteReservation> findALLByTitreLivreAndIsactifAttente(String titreLivre,Boolean actifAttente);
}