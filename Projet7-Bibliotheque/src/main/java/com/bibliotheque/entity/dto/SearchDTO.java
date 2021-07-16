package com.bibliotheque.entity.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String auteur;
    private String nomCategorie;
    private String titre;
}
