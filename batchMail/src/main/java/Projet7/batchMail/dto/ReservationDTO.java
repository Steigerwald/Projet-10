package Projet7.batchMail.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ReservationDTO {
    private int idReservation;
    private String etatReservation;
    private Date dateReservation;
    private Date dateDeRetrait;
    private Date info;
    private Date dateDeRetour;
    private int delaiDeLocation;
    private Boolean prolongation;
    private Boolean isactif;
    private Boolean relance;
    private UserDTO user;
    private LivreDTO livre;

    public String toStringInfo() {
        if (info != null) {
            SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd-MM-yyyy");
            return simpleDateFormat02.format(info);
        } else {
            return "pas encore retiré";
        }
    }

}