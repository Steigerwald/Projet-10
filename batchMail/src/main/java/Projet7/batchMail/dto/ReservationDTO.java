package Projet7.batchMail.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ReservationDTO {
    private int idReservation;
    private String etatReservation;
    private Date dateReservation;
    private Date dateDeRetrait;
    private Date dateMailInfo;
    private Date dateDeRetour;
    private int delaiDeLocation;
    private Boolean prolongation;
    private Boolean isactif;
    private Boolean relance;
    private UserDTO user;
    private LivreDTO livre;
}
