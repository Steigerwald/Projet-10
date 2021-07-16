package com.projet7.BibliothequeVille.entity.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
@Data
public class BibliothequeDTO {

    public String nomBibliotheque;

    public String lieu;

    public String adresse;

    public Long IdBibliotheque;

    public List<LivreDTO> livres;

    public List<UserDTO> users;


    @JsonCreator
    public BibliothequeDTO() {
    }
}


