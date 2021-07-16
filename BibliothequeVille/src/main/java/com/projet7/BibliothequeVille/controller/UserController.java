package com.projet7.BibliothequeVille.controller;

import com.projet7.BibliothequeVille.entity.User;
import com.projet7.BibliothequeVille.entity.dto.UserDTO;
import com.projet7.BibliothequeVille.entity.mapper.UserMapper;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/")
public class UserController {

    Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserService userService;

    public UserMapper userMapper;

    public UserController(UserMapper userMapper) { this.userMapper = userMapper; }


    /* Controller pour la page d'entrée sans connection */
    @RequestMapping(method = RequestMethod.GET)
    public String siteHome(Principal principal, Model model) {

        return "home/entree"; //view
    }

    /* controller pour afficher les users */
    @RequestMapping(path="users",method = RequestMethod.GET)
    public List<User> getAllUsers(Model model, Principal principal) {
        List<User> listUsers = userService.getAllUsers();
        return listUsers;
    }

    /* Controller pour afficher un user par Id */
    @RequestMapping(path="user/{id}", method = RequestMethod.GET)
    public User getReservationById(@PathVariable("id") Long id) throws RecordNotFoundException {
        User userTrouve = userService.getUserById(id);
        if (userTrouve!=null){
            return userTrouve;
        }else{
            return new User();
        }
    }

    /* Controller pour ajouter un user*/
    @RequestMapping(path="user/createUser",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public UserDTO createUser(@RequestBody UserDTO userDTO) throws RecordNotFoundException {
        User user =userMapper.toEntity(userDTO);
        userService.saveUser(user);
        return userMapper.toDto(user);
    }

}
