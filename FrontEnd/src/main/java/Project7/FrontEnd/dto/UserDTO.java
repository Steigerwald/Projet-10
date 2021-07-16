package Project7.FrontEnd.dto;

import lombok.Data;

@Data
public class UserDTO {

    private int idUser;
    private String nomUser;
    private String prenomUser;
    private String mailUser;
    private String motDePasse;
    private Boolean actifUser;
    private RoleDTO role;
}
