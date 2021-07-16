package com.projet7.BibliothequeVille.entity.dto;

import lombok.Data;

@Data
public class SearchDTO {

    private String auteur;
    private String nomCategorie;
    private String titre;


    //Constructeur
    public SearchDTO() {
    }


    // Getters

    public String getAuteur() {
        return auteur;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public String getTitre() {
        return titre;
    }


    // Setters

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
