package Project7.FrontEnd.form;

import Project7.FrontEnd.dto.UserDTO;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationForm {
    private int idReservation;
    private String etatReservation;
    private String dateReservation;
    private int delaiDeLocation;
}
