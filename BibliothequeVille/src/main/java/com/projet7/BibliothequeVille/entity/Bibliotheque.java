package com.projet7.BibliothequeVille.entity;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.projet7.BibliothequeVille.entity.dto.LivreDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TBL_BIBLIOTHEQUE")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"nomBibliotheque"})
public class Bibliotheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_BIBLIOTHEQUE")
    private Long IdBibliotheque;

    @Column(name="NOM_BIBLIOTHEQUE")
    private String nomBibliotheque;

    @Column(name="LIEU")
    private String lieu;

    @Column(name="ADRESSE")
    private String adresse;

    //@OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.ALL},orphanRemoval=true)
    @OneToMany(mappedBy = "bibliotheque",cascade = {CascadeType.ALL})
    //@JsonManagedReference (value="livre-movement")
    @JsonBackReference(value="livre-movement")
    @Nullable
    private List<Livre> livres;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TBL_BIBLIOTHEQUE_USERS", joinColumns = @JoinColumn(name = "bibliotheques_id_bibliotheque"),
            inverseJoinColumns = @JoinColumn(name = "users_id_user"))
    @Nullable
    @JsonBackReference
    private List<User> users;



    // Constructeur par defaut
    public Bibliotheque() {
    }


    // Getters

    public Long getIdBibliotheque() {
        return IdBibliotheque;
    }

    public String getNomBibliotheque() {
        return nomBibliotheque;
    }

    public String getLieu() {
        return lieu;
    }

    public String getAdresse() {
        return adresse;
    }

    @Nullable
    public List<Livre> getLivres() { return livres; }

    @Nullable
    public List<User> getUsers() {
        return users;
    }


    // Setters

    public void setIdBibliotheque(Long idBibliotheque) {
        IdBibliotheque = idBibliotheque;
    }

    public void setNomBibliotheque(String nomBibliotheque) {
        this.nomBibliotheque = nomBibliotheque;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setLivres(@Nullable List<Livre> livres) { this.livres = livres; }

    public void setUsers(@Nullable List<User> users) {
        this.users = users;
    }

}
