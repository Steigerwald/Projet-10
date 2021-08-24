package com.bibliotheque.controller;

import com.bibliotheque.entity.AttenteReservation;
import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.User;
import com.bibliotheque.entity.dto.AttenteReservationDTO;
import com.bibliotheque.entity.dto.LivreDTO;
import com.bibliotheque.entity.dto.ReservationDTO;
import com.bibliotheque.entity.dto.UserDTO;
import com.bibliotheque.entity.mapper.AttenteReservationMapper;
import com.bibliotheque.entity.mapper.LivreMapper;
import com.bibliotheque.entity.mapper.ReservationMapper;
import com.bibliotheque.entity.mapper.UserMapper;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.service.AttenteReservationService;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attenteReservation")
public class AttenteReservationController {

    @Autowired
    ReservationMapper reservationMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    LivreMapper livreMapper;

    @Autowired
    AttenteReservationMapper attenteReservationMapper;

    @Autowired
    AttenteReservationService attenteReservationService;

    @Autowired
    LivreService livreService;

    @Autowired
    UserService userService;

    Logger logger = (Logger) LoggerFactory.getLogger(AttenteReservationController.class);

    /* controller pour avoir toutes les attentes de reservation*/
    @RequestMapping(path ="/",method = RequestMethod.GET)
    public ResponseEntity<List<AttenteReservationDTO>> listOfAttenteReservations() {
        List<AttenteReservation> toutesAttenteReservations =attenteReservationService.findAllAttenteReservation();
        return new ResponseEntity<>(attenteReservationMapper.toDto(toutesAttenteReservations), HttpStatus.OK);
    }

    /* controller pour avoir toutes les attentes de reservation d'un livre*/
    @RequestMapping(path ="/livre/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<AttenteReservationDTO>> listOfAttenteReservationsByLivre(@PathVariable int id) {
        Livre livreTrouve = livreService.findById(id);
        List<AttenteReservation> toutesAttenteReservationsByTitreLivre =attenteReservationService.findAllAttenteReservationByTitreLivre(livreTrouve);
        return new ResponseEntity<>(attenteReservationMapper.toDto(toutesAttenteReservationsByTitreLivre), HttpStatus.OK);
    }


    /* controller pour avoir toutes les attentes de reservation d'un user*/
    @RequestMapping(path ="/user/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<AttenteReservationDTO>> listOfAttenteReservationsByUser(@PathVariable int id) throws RecordNotFoundException {
        User userTrouve = userService.getUserById(id);
        List<AttenteReservation> toutesAttenteReservationsByUser =attenteReservationService.findAllAttenteReservationByUser(userTrouve);
        //return new ResponseEntity<>(attenteReservationMapper.toDto(toutesAttenteReservationsByUser), HttpStatus.OK);
        return new ResponseEntity<>(toutesAttenteReservationsByUser.stream().map(x ->attenteReservationMapper.fromEntityToDtoWithoutUser(x)).collect(Collectors.toList()), HttpStatus.OK);
    }


    /* controller pour obtenir une attente de reservation */
    @RequestMapping(path ="/{id}",method = RequestMethod.GET)
    public ResponseEntity<AttenteReservationDTO> attenteReservationId(@PathVariable int id){
        AttenteReservation attenteReservation =attenteReservationService.findAttenteReservationById(id);
        if (attenteReservation==null){
            return new ResponseEntity<>(attenteReservationMapper.toDto(attenteReservation), HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(attenteReservationMapper.toDto(attenteReservation), HttpStatus.OK);
        }
    }

    /* controller pour ajouter une attente de reservation */
    @RequestMapping(path = "/addAttenteReservation",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<AttenteReservationDTO> newAttenteReservation(@RequestBody AttenteReservationDTO attenteReservationDTO) throws RecordNotFoundException {
        String titreLivre =attenteReservationMapper.toEntity(attenteReservationDTO).getTitreLivre();
        List<Livre> listeLivresTrouves =livreService.getAllLivresByTitre(titreLivre);
        AttenteReservation attenteReservation=new AttenteReservation();
        if (listeLivresTrouves.size()>0){
            Livre livreRef=listeLivresTrouves.get(0);
            attenteReservation =attenteReservationService.createAttenteReservation(attenteReservationMapper.toEntity(attenteReservationDTO).getUser(),livreRef);
        }
        return new ResponseEntity<>(attenteReservationMapper.toDto(attenteReservation), HttpStatus.OK);
    }


    /* controller pour effacer une attente de reservation de la base de données */
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void deleteAttenteReservationById(Model model, @PathVariable("id") int id) throws RecordNotFoundException {
        attenteReservationService.deleteAttenteReservationById(id);
    }

    /* controller pour annuler une attente de reservation */
    @RequestMapping(path = "/annulerAttenteReservation",method = RequestMethod.PUT,produces = "application/json")
    public ResponseEntity<AttenteReservationDTO> updateAttenteReservation(@RequestBody AttenteReservationDTO attenteReservationAnnuleeDTO) throws RecordNotFoundException {
        AttenteReservation attenteReservationAnnulee = attenteReservationMapper.toEntity(attenteReservationAnnuleeDTO);
        AttenteReservationDTO dto=attenteReservationMapper.toDto(attenteReservationService.annulerAttenteReservation(attenteReservationAnnulee));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /* controller pour avoir la liste d'attente des users pour un livre*/
    @RequestMapping(path ="/Users/livre/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> listOfAttenteReservationsofUsersByLivre(@PathVariable int id) {
        Livre livreTrouve = livreService.findById(id);
        List<User> tousLesUsersEnAttente =attenteReservationService.ordonnerlisteAttenteUsers(livreTrouve);
        return new ResponseEntity<>(userMapper.toDto(tousLesUsersEnAttente), HttpStatus.OK);
    }

    /* controller pour avoir la position d'un user dans une liste d'attente d'un livre*/
    @RequestMapping(path ="/User/Position/livre/{id}&&{idUser}",method = RequestMethod.GET)
    public int positionUserListeDAtente(@PathVariable int id,@PathVariable int idUser) throws RecordNotFoundException {
        Livre livreTrouve = livreService.findById(id);
        User user =userService.getUserById(idUser);
        int position =attenteReservationService.avoirPositionUser(user,livreTrouve);
        //String j= String.valueOf(position);
        //return j;
        return position;
        //return new ResponseEntity<>(userMapper.toDto(tousLesUsersEnAttente), HttpStatus.OK);
    }

    /* controller pour vérifier que le nombre d'attentes de la liste d'attente d'un livre < 2 fois le nombre d'exemplaires*/
    @RequestMapping(path ="/nombre/livre/{id}",method = RequestMethod.GET)
    public Boolean verifierTailleListeDAttenteEtNombreExemplaires(@PathVariable int id) {
        Livre livreTrouve = livreService.findById(id);
        Boolean verification = attenteReservationService.verifierNombreListeAttente(livreTrouve);
        return verification;
    }

    /*Controller pour vérifier que le user n'est pas dans la liste d'attente pour ce livre*/
    @RequestMapping(path ="/User/VerifierAttenteReservation/livre/{idLivre}&&{idUser}",method = RequestMethod.GET)
    public Boolean verifierAttenteReservationByUserAndLivre(@PathVariable int idLivre,@PathVariable int idUser) throws RecordNotFoundException {
        Livre livreTrouve = livreService.findById(idLivre);
        User user = userService.getUserById(idUser);
        Boolean result = attenteReservationService.verifierUserListeDAttente(user, livreTrouve);
        return result;
    }

    /* controller pour avoir l'attente de reservation d'un user concernant un titre de livre*/
    @RequestMapping(path ="/user/livre/{idLivre}&&{idUser}",method = RequestMethod.GET)
    public ResponseEntity<AttenteReservationDTO> attenteReservationsByLivreAndByUser(@PathVariable int idLivre,@PathVariable int idUser) throws RecordNotFoundException {
        Livre livreTrouve = livreService.findById(idLivre);
        User userTrouve = userService.getUserById(idUser);
        AttenteReservation attenteReservation =attenteReservationService.findAllAttenteReservationByTitreLivreAndIsactifAndUser(livreTrouve,userTrouve);
        return new ResponseEntity<>(attenteReservationMapper.toDto(attenteReservation), HttpStatus.OK);

    }

    /* controller pour avoir toutes les attentes de réservation à relancer 1 fois qui sont à traiter par le batch*/

}
