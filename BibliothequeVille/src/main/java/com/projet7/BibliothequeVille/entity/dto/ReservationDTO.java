package com.projet7.BibliothequeVille.entity.dto;
import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.User;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class ReservationDTO {

   private Long IdReservation;
   private String etatReservation;
   private Date dateReservation;
   private Date dateDeRetrait;
   private String delaiDeLocation;
   private Boolean isactif;
   private UserDTO user;
   private List<LivreDTO> livres;



   //Constructeur
   public ReservationDTO() {
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

   public String getDelaiDeLocation() {
      return delaiDeLocation;
   }

   public Boolean getIsactif() {
      return isactif;
   }

   public UserDTO getUser() { return user; }

   public List<LivreDTO> getLivres() { return livres; }


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

   public void setDelaiDeLocation(String delaiDeLocation) {
      this.delaiDeLocation = delaiDeLocation;
   }

   public void setIsactif(Boolean isactif) {
      this.isactif = isactif;
   }

   public void setUser(UserDTO user) { this.user = user; }

   public void setLivres(List<LivreDTO> livres) { this.livres = livres; }


}
