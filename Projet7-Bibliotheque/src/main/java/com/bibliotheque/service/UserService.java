package com.bibliotheque.service;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Role;
import com.bibliotheque.entity.User;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.form.LoginForm;
import com.bibliotheque.repository.RoleRepository;
import com.bibliotheque.repository.UserRepository;
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

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /*Methode pour transformer un mot de passe en mot de passe codé*/
    public String getMotDePasseCode(String motDePasse){
        return passwordEncoder.encode(motDePasse);
    }

    /*Methode pour vérifier un mot de passe en mot de passe codé*/
    public Boolean verificationMotDePasse(String motDePasse, LoginForm user){
        return passwordEncoder.matches(motDePasse,customUserDetailsService.loadUserByUsername(user.getUserName()).getPassword());
    }

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
        logger.info(" le mail recherché est:"+mail);
        if(userTrouve.isPresent()) {
            logger.info(" retour du userTrouve grâce au mail car il est présent ");
            return userTrouve.get();
        } else {
            logger.info("Pas de user d'enregistré avec ce mail");
            return null ;
        }
    }

    /*Methode pour voir un user par Id*/
    public User getUserById(int id) throws RecordNotFoundException {
        Optional<User> userTrouve = userRepository.findByIdUser(id);
        if(userTrouve.isPresent()) {
            logger.info(" retour du userTrouve car il est présent ");
            return userTrouve.get();
        } else {
            throw new RecordNotFoundException("Pas de user enregistré avec cet Id");
        }
    }

    /*Methode pour sauvegarder dans une base de données un User*/
    public User saveUser (User user) throws RecordNotFoundException {
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        user.setActifUser(true);
        user.setRole(getRoleById(2));
        logger.info(" récupération du mot de passe et l'encode pour l'enregistrer");
        return userRepository.save(user);
    }

    /*Methode pour modifier un User*/
    public User updateUser (User user) throws RecordNotFoundException {
        Optional<User> userAModifier = userRepository.findByIdUser(user.getIdUser());
        if(userAModifier.isPresent())
        { logger.info(" l'entité user à modifier a été trouvée et peut être modifiée");
            if (user.getReservations()==null) {
                user.setReservations(userAModifier.get().getReservations());
            }
            return userRepository.save(user);
        } else {
            throw new RecordNotFoundException("Pas d'entité User enregistrée avec cet Id et elle ne peut pas être modifiée");
        }
    }

    /*Methode pour voir un role par Id*/
    public Role getRoleById(int id) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findByIdRole(id);
        if(role.isPresent()) {
            logger.info(" retour du role car il est présent ");
            return role.get();
        } else {
            throw new RecordNotFoundException("Pas de role enregistré avec cet Id");
        }
    }

    /*Methode pour avoir tous les roles enregistrés dans la base de données*/
    public List<Role> getAllRoles() {
        List<Role> resultRole = (List<Role>) roleRepository.findAll();
        if(resultRole.size() > 0) {
            logger.info(" retour liste resultRole car resultRole n'est pas vide ");
            return resultRole;
        } else {
            logger.info(" retour nouvelle liste vide car pas d'élément dans la liste resultRole de getAllRoles ");
            return new ArrayList<Role>();
        }
    }


    /*Methode pour effacer un user dans la base de données*/
    public void deleteUserById(int id) throws RecordNotFoundException {
        Optional<User> userAEffacer = userRepository.findById(id);
        if(userAEffacer.isPresent()) {
            User userTrouve = userAEffacer.get();
            userRepository.deleteById(userTrouve.getIdUser());
        } else {
            throw new RecordNotFoundException("Pas de livre enregistré avec cet Id");
        }
    }

    /*Methode pour désactiver un user dans la base de données*/
    public void desactiveUserById(int id) throws RecordNotFoundException {
        Optional<User> userADesactiver = userRepository.findById(id);
        if(userADesactiver.isPresent()) {
            User userTrouve = userADesactiver.get();
            userTrouve.setActifUser(false);
            updateUser(userTrouve);
        } else {
            throw new RecordNotFoundException("Pas de livre enregistré avec cet Id");
        }
    }

    /*Methode pour réactiver un user dans la base de données*/
    public void reactiveUserById(int id) throws RecordNotFoundException {
        Optional<User> userAReactiver = userRepository.findById(id);
        if(userAReactiver.isPresent()) {
            User userTrouve = userAReactiver.get();
            userTrouve.setActifUser(true);
            updateUser(userTrouve);
        } else {
            throw new RecordNotFoundException("Pas de livre enregistré avec cet Id");
        }
    }



}
