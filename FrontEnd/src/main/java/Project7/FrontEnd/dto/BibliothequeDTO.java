package Project7.FrontEnd.dto;

import lombok.Data;

@Data
public class BibliothequeDTO {
    private int idBibliotheque;
    private String nomBibliotheque;
    private String lieu;
    private String adresse;

    @Override
    public String toString() {
        return "BibliothequeDTO{" +
                "idBibliotheque=" + idBibliotheque +
                ", nomBibliotheque='" + nomBibliotheque + '\'' +
                ", lieu='" + lieu + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
