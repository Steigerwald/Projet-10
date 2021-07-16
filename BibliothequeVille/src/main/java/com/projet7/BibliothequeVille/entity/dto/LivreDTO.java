package com.projet7.BibliothequeVille.entity.dto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LivreDTO implements Serializable {

    private Long IdLivre;
    private String titre;
    private String description;
    private String auteur;
    private Integer nombrePages;
    private String nomCategorie;
    private String etatLivre;
    private Date dateAchat;
    private Long prixLocation;
    private Boolean disponibilite;
    private BibliothequeDTO bibliotheque;
    private ReservationDTO reservation;



    //Constructeur
    public LivreDTO() {
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
        return auteur;
    }

    public Integer getNombrePages() { return nombrePages; }

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

    public Boolean getDisponibilite() { return disponibilite; }

    public BibliothequeDTO getBibliotheque() { return bibliotheque; }

    public ReservationDTO getReservation() { return reservation; }




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

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setNombrePages(Integer nombrePages) {
        this.nombrePages = nombrePages;
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

    public void setDisponibilite(Boolean disponibilite) { this.disponibilite = disponibilite; }

    public void setBibliotheque(BibliothequeDTO bibliotheque) { this.bibliotheque = bibliotheque; }

    public void setReservation(ReservationDTO reservation) { this.reservation = reservation; }


}
