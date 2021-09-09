package com.bibliotheque.entity;

import lombok.Data;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_RESERVATION")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_RESERVATION")
    private int idReservation;

    @Column(name="ETAT_RESERVATION")
    private String etatReservation;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="DATE_RESERVATION")
    private Date dateReservation;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="DATE_RETRAIT")
    @Nullable
    private Date dateDeRetrait;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="INFO")
    @Nullable
    private Date info;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="DATE_RETOUR")
    @Nullable
    private Date dateDeRetour;

    @Column(name="DELAI_LOCATION")
    private int delaiDeLocation;

    @Column (name="PROLONGATION")
    private Boolean prolongation;

    @Column(name="ISACTIF")
    private Boolean isactif;

    @Column(name="RELANCE")
    private Boolean relance;

    @ManyToOne
    private User user;

    @ManyToOne
    @Nullable
    private Livre livre;


}
