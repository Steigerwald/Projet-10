package com.projet7.BibliothequeVille.entity.dto;


import com.projet7.BibliothequeVille.entity.Bibliotheque;
import com.projet7.BibliothequeVille.entity.Reservation;
import com.projet7.BibliothequeVille.entity.Role;
import lombok.Data;

import java.util.Collection;

@Data
public class UserDTO {

    private Long idUser;
    private String nomUser;
    private String prenomUser;
    private String mailUser;
    private String motDePasse;
    private Boolean actifUser;
    private Role role;
    private Collection<ReservationDTO> reservations;
    private Collection<BibliothequeDTO> bibliotheques;


    // Constructeur
    public UserDTO() {
    }


    // Getters

    public Long getIdUser() {
        return idUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public String getMailUser() {
        return mailUser;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Boolean getActifUser() {
        return actifUser;
    }

    public Role getRole() { return role; }

    public Collection<ReservationDTO> getReservations() { return reservations; }

    public Collection<BibliothequeDTO> getBibliotheques() { return bibliotheques; }


    // Setters

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setActifUser(Boolean actifUser) {
        this.actifUser = actifUser;
    }

    public void setRole(Role role) { this.role = role; }

    public void setReservations(Collection<ReservationDTO> reservations) { this.reservations = reservations; }

    public void setBibliotheques(Collection<BibliothequeDTO> bibliotheques) { this.bibliotheques = bibliotheques; }


}
