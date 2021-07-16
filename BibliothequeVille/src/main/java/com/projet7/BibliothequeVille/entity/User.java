package com.projet7.BibliothequeVille.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="TBL_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_USER")
    private Long idUser;

    @Column(name="NOM_USER")
    @NotNull
    private String nomUser;

    @Column(name="PRENOM_USER")
    @NotNull
    private String prenomUser;

    @Column(nullable=false, unique=true,name="MAIL_USER")
    @NotNull
    private String mailUser;

    @Column(name="MOT_PASSE")
    @NotNull
    private String motDePasse;

    @Column(name="ACTIF_USER")
    private Boolean actifUser;

    @ManyToOne
    @JsonManagedReference
    private Role role;

    @OneToMany(mappedBy="user",fetch=FetchType.LAZY,orphanRemoval=true)
    @Nullable
    @JsonBackReference
    private Collection<Reservation> reservations;

    @ManyToMany(mappedBy="users",fetch=FetchType.LAZY)
    @Nullable
    //@JsonBackReference(value="user-movement")
    private Collection<Bibliotheque> bibliotheques;


    // Constructeur par défaut
    public User() {
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

    public Boolean getActifUser() { return actifUser; }

    public Role getRole() { return role; }

    @Nullable
    public Collection<Reservation> getReservations() { return reservations; }

    @Nullable
    public Collection<Bibliotheque> getBibliotheques() { return bibliotheques; }


    // Setters

    public void setIdUser(Long idUser) {
        idUser = idUser;
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

    public void setActifUser(Boolean actifUser) { this.actifUser = actifUser; }

    public void setRole(Role role) { this.role = role; }

    public void setReservations(@Nullable Collection<Reservation> reservations) { this.reservations = reservations; }

    public void setBibliotheques(@Nullable Collection<Bibliotheque> bibliotheques) { this.bibliotheques = bibliotheques; }


}
