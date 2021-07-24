package com.bibliotheque.entity.dto;

import lombok.Data;
import java.util.Date;

@Data
public class AttenteReservationDTO {

    private int idAttenteReservation;
    private String etatAttenteReservation;
    private Date dateAttenteReservation;
    private Boolean dateDelaiDepasse;
    private Boolean isactifAttente;
    private UserDTO user;
    private LivreDTO livre;
}
