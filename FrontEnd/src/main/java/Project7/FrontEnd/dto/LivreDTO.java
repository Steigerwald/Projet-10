package Project7.FrontEnd.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class LivreDTO {

    private int idLivre;
    private String titre;
    private String auteur;
    private Date publication;
    private String resume;
    private String nombrePages;
    private String nomCategorie;
    private Date dateAchat;
    private int prixLocation;
    private String etatLivre;
    private Boolean disponibilite;
    private BibliothequeDTO bibliotheque;

    public String toStringDatePublication() {

        SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat("MM-yyyy");
        return simpleDateFormat01.format(publication);
    }

    public String toStringDateAchat(){
        SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat02.format(dateAchat);
    }

    public String toStringDisponibilite(){
        if (disponibilite){
            return "oui";
        }else{
            return "non";
        }
    }


}
