package com.projet7.BibliothequeVille.service;

import com.projet7.BibliothequeVille.entity.Role;
import com.projet7.BibliothequeVille.entity.User;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.repository.RoleRepository;
import com.projet7.BibliothequeVille.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /*Methode pour avoir tous les users enregistrés dans la base de données*/
    public List<User> getAllUsers() {
        List<User> resultUser = (List<User>) userRepository.findAll();
        if(resultUser.size() > 0) {
            logger.info(" retour liste resultUser car resultUser n'est pas vide ");
            return resultUser;
        } else {
            logger.info(" retour nouvelle liste vide car pas d'élément dans la liste resultUser de getAllUsers ");
            return new ArrayList<User>();
        }
    }

    /*Methode pour obtenir un User par par une adresse mail*/
    public User getUserByMail(String mail) {
        Optional<User> userTrouve = userRepository.findByMailUser(mail);
        if(userTrouve.isPresent()) {
            logger.info(" retour du userTrouve grâce au mail car il est présent ");
            return userTrouve.get();
        } else {
            logger.info("Pas de user d'enregistré avec ce mail");
            return null ;
        }
    }

    /*Methode pour voir un user par Id*/
    public User getUserById(Long id) throws RecordNotFoundException {
        Optional<User> userTrouve = userRepository.findByIdUser(id);
        if(userTrouve.isPresent()) {
            logger.info(" retour du userTrouve car il est présent ");
            return userTrouve.get();
        } else {
            throw new RecordNotFoundException("Pas de user enregistré avec cet Id");
        }
    }


    /*Methode pour sauvegarder dans une base de données un User*/
    public void saveUser (User user) {
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        logger.info(" récupération du mot de passe et l'encode pour l'enregistrer");
        userRepository.save(user);
    }

    /*Methode pour modifier un User*/
    public void updateUser (User user) throws RecordNotFoundException {
        Optional<User> userAModifier = userRepository.findByIdUser(user.getIdUser());
        if(userAModifier.isPresent())
        { logger.info(" l'entité user à modifier a été trouvée et peut être modifiée");
            User newUser=userAModifier.get();
            newUser.setNomUser(user.getNomUser());
            newUser.setPrenomUser(user.getPrenomUser());
            newUser.setMailUser(user.getMailUser());
            newUser.setRole(user.getRole());
            userRepository.save(newUser);
        } else {
            throw new RecordNotFoundException("Pas d'entité User enregistrée avec cet Id et elle ne peut pas être modifiée");
        }
    }

    /*Methode pour voir un role par Id*/
    public Role getRoleById(Long id) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findByIdRole(id);
        if(role.isPresent()) {
            logger.info(" retour du role car il est présent ");
            return role.get();
        } else {
            throw new RecordNotFoundException("Pas de role enregistré avec cet Id");
        }
    }

}
