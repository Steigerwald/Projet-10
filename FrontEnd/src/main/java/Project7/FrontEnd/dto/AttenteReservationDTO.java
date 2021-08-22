package Project7.FrontEnd.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
public class AttenteReservationDTO {

    private int idAttenteReservation;
    private String etatAttenteReservation;
    private Date dateAttenteReservation;
    private Boolean dateDelaiDepasse;
    private Boolean isactifAttente;
    private String titreLivre;
    private int positionUser;
    private UserDTO user;

    public String toStringDateAttenteReservation() {
        SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat01.format(dateAttenteReservation);
    }

}
