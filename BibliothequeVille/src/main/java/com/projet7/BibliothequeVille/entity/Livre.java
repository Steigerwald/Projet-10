package com.projet7.BibliothequeVille.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="TBL_LIVRE")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_LIVRE")
    private Long IdLivre;

    @Column(name="TITRE")
    private String titre;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="AUTEUR")
    private String auteur;

    @Column(name="NOMBRE_PAGES")
    private Integer nombrePages;

    @Column(name="DISPONIBILITE")
    private Boolean disponibilite;

    @Column(name="NOM_CATEGORIE")
    private String nomCategorie;

    @Column(name="ETAT_LIVRE")
    private String etatLivre;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="DATE_ACHAT")
    private Date dateAchat;

    @Column(name="PRIX_LOCATION")
    private Long prixLocation;

    @ManyToOne (fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
    @Nullable
    //@JsonManagedReference (value="livre-movement")
    //@JsonBackReference(value="livre-movement")
    private Bibliotheque bibliotheque;

    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
    @Nullable
    //@JsonBackReference
    private Reservation reservation;




    // Constructeur par défaut
    public Livre() {
    }


    // Getters

    public Long getIdLivre() {
        return IdLivre;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getAuteur() {
        return auteur; }

    public Integer getNombrePages() {
        return nombrePages;
    }

    public Boolean getDisponibilite() {
        return disponibilite;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public String getEtatLivre() {
        return etatLivre;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public Long getPrixLocation() {
        return prixLocation;
    }

    @Nullable
    public Bibliotheque getBibliotheque() { return bibliotheque; }

    @Nullable
    public Reservation getReservation() { return reservation; }


    // Setters

    public void setIdLivre(Long idLivre) {
        IdLivre = idLivre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuteur(String auteur) { this.auteur = auteur; }

    public void setNombrePages(Integer nombrePages) {
        this.nombrePages = nombrePages;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public void setEtatLivre(String etatLivre) {
        this.etatLivre = etatLivre;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public void setPrixLocation(Long prixLocation) {
        this.prixLocation = prixLocation;
    }

    public void setBibliotheque(@Nullable Bibliotheque bibliotheque) { this.bibliotheque = bibliotheque; }

    public void setReservation(@Nullable Reservation reservation) { this.reservation = reservation; }

    @Override
    public String toString() {
        return "Livre{" +
                "IdLivre=" + IdLivre +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", auteur='" + auteur + '\'' +
                ", nombrePages=" + nombrePages +
                ", disponibilite=" + disponibilite +
                ", nomCategorie='" + nomCategorie + '\'' +
                ", etatLivre='" + etatLivre + '\'' +
                ", dateAchat=" + dateAchat +
                ", prixLocation=" + prixLocation +
                '}';
    }
}
