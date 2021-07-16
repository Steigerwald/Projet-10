package Project7.FrontEnd.form;

import lombok.Data;


@Data
public class LivreForm {
    private int idLivre;
    private String titre;
    private String auteur;
    private String publication;
    private String resume;
    private String nombrePages;
    private String nomCategorie;
    private String dateAchat;
    private int prixLocation;
    private String etatLivre;
    private String bibliotheque;
    }

