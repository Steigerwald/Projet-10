package Projet7.batchMail.dto;

import lombok.Data;
import java.util.Date;

@Data
public class AttenteReservationDTO {

    private int idAttenteReservation;
    private String etatAttenteReservation;
    private Date dateAttenteReservation;
    private Boolean dateDelaiDepasse;
    private Boolean isactifAttente;
    private String titreLivre;
    private UserDTO user;
    private int positionUser;
}
