package Projet7.batchMail.controller;

import Projet7.batchMail.service.AuthService;
import Projet7.batchMail.service.UserService;
import Projet7.batchMail.dto.UserDTO;
import Projet7.batchMail.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {

    Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    @Autowired
    public AuthService authService;

    @Autowired
    public UserService userService;

    /* controller de la page de login */
    @RequestMapping(path="user/login",method = RequestMethod.GET)
    public String formLogin(Model model){
        LoginForm newUser =new LoginForm();
        model.addAttribute("utilisateur",newUser);
        UserDTO user = new UserDTO();
        model.addAttribute("user",user);
        logger.info(" on est dans la page du login");
        return "userLogin";
    }

    /* controller pour recevoir un mail et mot de passe pour obtenir l'autorisation*/
    @RequestMapping(value="login",method = RequestMethod.POST)
    public String getUserConnexion(LoginForm utilisateur, Principal principal, Model model) throws IOException, InterruptedException {
        logger.info(" on est dans la requete post  du login ");
        logger.info(" le mail de l'uilisateur est: "+utilisateur.getUserName());
        String token =userService.getTokenByMailAndMotDePasse(utilisateur);
        switch (token) {
            case "not found" :
                authService.setAuthentification(false);
                authService.setUserConnecte(null);
                return "redirect:/user/login?error";
            case "mot de passe invalide":
                authService.setAuthentification(false);
                authService.setUserConnecte(null);
                return "redirect:/user/login?error";
            case "null":
                authService.setAuthentification(false);
                authService.setUserConnecte(null);
                return "redirect:/user/login?error";
            default:
                authService.setMemoireToken(token);
                authService.setAuthentification(true);
                UserDTO newUserDTO=new UserDTO();
                newUserDTO.setMailUser(utilisateur.getUserName());
                return "pageReussie";
        }
    }

}
