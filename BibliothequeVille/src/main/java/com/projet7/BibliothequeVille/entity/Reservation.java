package com.projet7.BibliothequeVille.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="TBL_RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_RESERVATION")
    private Long IdReservation;

    @Column(name="ETAT_RESERVATION")
    private String etatReservation;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="DATE_RESERVATION")
    private Date dateReservation;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="DATE_RETRAIT")
    @Nullable
    private Date dateDeRetrait;

    @Column(name="DELAI_LOCATION")
    private Integer delaiDeLocation;

    @Column(name="ISACTIF")
    private Boolean isactif;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "reservation",fetch=FetchType.LAZY,orphanRemoval=true)
    @JsonManagedReference
    @Nullable
    private Collection<Livre> livres;


    // Constructeur par défaut
    public Reservation() {
    }


    // Getters

    public Long getIdReservation() {
        return IdReservation;
    }

    public String getEtatReservation() {
        return etatReservation;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public Date getDateDeRetrait() {
        return dateDeRetrait;
    }

    public Integer getDelaiDeLocation() {
        return delaiDeLocation;
    }

    public Boolean getIsactif() { return isactif; }

    public User getUser() { return user; }

    @Nullable
    public Collection<Livre> getLivres() { return livres; }




    // Setters

    public void setIdReservation(Long idReservation) {
        IdReservation = idReservation;
    }

    public void setEtatReservation(String etatReservation) {
        this.etatReservation = etatReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public void setDateDeRetrait(Date dateDeRetrait) {
        this.dateDeRetrait = dateDeRetrait;
    }

    public void setDelaiDeLocation(Integer delaiDeLocation) {
        this.delaiDeLocation = delaiDeLocation;
    }

    public void setIsactif(Boolean isactif) { this.isactif = isactif; }

    public void setUser(User user) { this.user = user; }

    public void setLivres(@Nullable Collection<Livre> livres) { this.livres = livres; }

}
