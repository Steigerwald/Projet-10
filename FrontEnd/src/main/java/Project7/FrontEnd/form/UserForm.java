package Project7.FrontEnd.form;

import Project7.FrontEnd.dto.RoleDTO;
import lombok.Data;

@Data
public class UserForm {
    private int idUser;
    private String nomUser;
    private String prenomUser;
    private String mailUser;
    private String motDePasse;
}
