package Projet7.batchMail.dto;

import lombok.Data;

@Data
public class UserDTO {
    public int idUser;
    private String nomUser;
    private String prenomUser;
    private String mailUser;
    private String motDePasse;
    private Boolean actifUser;
    private RoleDTO role;
}
