package Project7.FrontEnd.dto;

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

    public String toStringDateReservation() {
        SimpleDateFormat simpleDateFormat01 = new SimpleDateFormat("MM-yyyy");
        return simpleDateFormat01.format(dateReservation);
    }

    public String toStringDateDeRetrait() {
        if (dateDeRetrait != null) {
            SimpleDateFormat simpleDateFormat02 = new SimpleDateFormat("dd-MM-yyyy");
            return simpleDateFormat02.format(dateDeRetrait);
        }else {
            return "pas encore retiré";
        }
    }

    public String toStringDateDeRetour() {
        if (dateDeRetour != null) {
            SimpleDateFormat simpleDateFormat03 = new SimpleDateFormat("dd-MM-yyyy");
            return simpleDateFormat03.format(dateDeRetour);
        }else {
            return "pas encore retourné";
        }
    }

    public String toStringProlongation(){
        if (prolongation){
            return "oui";
        }else{
            return "non";
        }
    }


}
