package com.bibliotheque.entity;

import com.bibliotheque.entity.dto.LivreDTO;
import com.bibliotheque.entity.dto.UserDTO;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_ATTENTE_RESERVATION")
@Data
public class AttenteReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_ATTENTE_RESERVATION")
    private int idAttenteReservation;

    @Column(name="ETAT_ATTENTE_RESERVATION")
    private String etatAttenteReservation;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="DATE_ATTENTE_RESERVATION")
    private Date dateAttenteReservation;

    @Column(name="DATE_DELAI_DEPASSE")
    private Boolean datedelaiDepasse;

    @Column(name="ISACTIF_ATTENTE")
    private Boolean isactifAttente;

    @Column(name="TITRE_LIVRE")
    private String titreLivre;

    //@ManyToOne(fetch =FetchType.LAZY)
    @ManyToOne
    private User user;

}