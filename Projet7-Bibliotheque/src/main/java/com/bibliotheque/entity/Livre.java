package com.bibliotheque.entity;


import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "TBL_LIVRE")
@Data
public class Livre {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="ID_LIVRE")
    private int idLivre;

    @Column(name="TITRE")
    private String titre;

    @Column(name="AUTEUR")
    private String auteur;

    @Column(name="PUBLICATION")
    private Date publication;

    @Column(name="RESUME")
    private String resume;

    @Column(name="NOMBRE_PAGES")
    private String nombrePages;

    @Column(name="NOM_CATEGORIE")
    private String nomCategorie;

    @Column(name="DATE_ACHAT")
    private Date dateAchat;

    @Column(name="PRIX_LOCATION")
    private int prixLocation;

    @Column(name="ETAT_LIVRE")
    private String etatLivre;

    @Column(name="DISPONIBILITE")
    private Boolean disponibilite;

    @ManyToOne
    @Nullable
    private Bibliotheque bibliotheque;

    @OneToMany(mappedBy = "livre",fetch=FetchType.LAZY)
    private List<Reservation> reservations;

    /*
    @OneToMany(mappedBy="livre",fetch=FetchType.LAZY,orphanRemoval=true)
    @Nullable
    private List<AttenteReservation> attenteReservations;
     */

}
