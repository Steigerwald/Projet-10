package com.projet7.BibliothequeVille.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="TBL_ROLE")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="ID_ROLE")
   private Long idRole;

   @Column(name="NOM_ROLE")
   private String nomRole;

   @Column(name="ACTIF_ROLE")
   private Boolean actifRole;

    @OneToMany(mappedBy = "role",fetch=FetchType.LAZY,orphanRemoval = true)
    @JsonBackReference
    @Nullable
    private Collection<User> users;



   // Constructeur par défaut
    public Role() {
    }


    //Getters

    public Long getIdRole() {
        return idRole;
    }

    public String getNomRole() {
        return nomRole;
    }

    public Boolean getActifRole() {
        return actifRole;
    }

    @Nullable
    public Collection<User> getUsers() { return users; }


    //Setters


    public void setIdRole(Long idRole) {
        idRole = idRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    public void setActifRole(Boolean actifRole) {
        this.actifRole = actifRole;
    }

    public void setUsers(@Nullable Collection<User> users) { this.users = users; }

}
