package com.bibliotheque.controller;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.Reservation;
import com.bibliotheque.entity.User;
import com.bibliotheque.entity.dto.LivreDTO;
import com.bibliotheque.entity.dto.ReservationDTO;
import com.bibliotheque.entity.dto.UserDTO;
import com.bibliotheque.entity.mapper.LivreMapper;
import com.bibliotheque.entity.mapper.ReservationMapper;
import com.bibliotheque.entity.mapper.UserMapper;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.ReservationService;
import com.bibliotheque.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    LivreService livreService;

    @Autowired
    UserService userService;

    @Autowired
    ReservationMapper reservationMapper;


    @Autowired
    UserMapper userMapper;

    @Autowired
    LivreMapper livreMapper;

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationController.class);


    /* controller pour avoir toutes les reservations*/
    @RequestMapping(path ="/",method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO> >listOfReservations() {
        List<Reservation> toutesReservations =reservationService.findAll();
        return new ResponseEntity<>(reservationMapper.toDto(toutesReservations), HttpStatus.OK);
    }

    /* controller pour avoir la reservation avec la date de retrait la plus ancienne par livre*/
    @RequestMapping(path ="/byLivreByDateRetraitAncienne/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO>>getListReservationOrdonnee(@PathVariable int id) throws RecordNotFoundException {
        Livre leLivre =livreService.findById(id);
        List<Reservation> listReservationsOrdonnees = reservationService.findReservationByLivreWithDateRetraitLaPlusAncienne(leLivre);
        if (listReservationsOrdonnees.size()>0){
            return new ResponseEntity<>(reservationMapper.toDto(listReservationsOrdonnees), HttpStatus.OK);
        }else {
            List<ReservationDTO> newList =new ArrayList<>();
            return new ResponseEntity<>(newList, HttpStatus.OK);
        }
    }

    /* controller pour obtenir une reservation */
    @RequestMapping(path ="/{id}",method = RequestMethod.GET)
    public ResponseEntity<ReservationDTO> reservationId(@PathVariable int id) {
        Reservation laReservation =reservationService.findById(id);
        if (laReservation==null){
            return new ResponseEntity<>(reservationMapper.toDto(laReservation), HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(reservationMapper.toDto(laReservation), HttpStatus.OK);
        }
    }

    /* controller pour ajouter une reservation */
    @RequestMapping(path = "/addReservation",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<ReservationDTO> newBReservation(@RequestBody ReservationDTO reservationDTO) throws RecordNotFoundException {
        System.out.println("reservation => " + reservationMapper.toEntity(reservationDTO));
        Reservation reservation =reservationService.createReservation(reservationMapper.toEntity(reservationDTO));
        return new ResponseEntity<>(reservationMapper.toDto(reservation), HttpStatus.OK);
    }

    /* controller pour modifier une reservation */
    @RequestMapping(path = "/",method = RequestMethod.PUT,produces = "application/json")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationModifieDTO) throws RecordNotFoundException {
        Reservation reservationModifie = reservationMapper.toEntity(reservationModifieDTO);
        Reservation reservation=reservationService.updateReservation(reservationModifie);
        ReservationDTO dto = reservationMapper.toDto(reservation);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /* controller pour effacer une reservation de la base de données */
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void deleteReservationById(Model model, @PathVariable("id") int id) throws RecordNotFoundException{
       reservationService.deleteReservationById(id);
    }

    /* controller pour annuler une reservation */
    @RequestMapping(path = "/annulerReservation",method = RequestMethod.PUT,produces = "application/json")
    public ResponseEntity<ReservationDTO> annulerReservation(@RequestBody ReservationDTO reservationAnnuleeDTO) throws RecordNotFoundException {
        Reservation reservationAnnulee = reservationMapper.toEntity(reservationAnnuleeDTO);
        ReservationDTO dto = reservationMapper.toDto(reservationService.annulerReservation(reservationAnnulee));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /* controller pour avoir toutes les reservations à valider*/
    @RequestMapping(path ="/AValider",method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO> >listOfReservationsAValider() {
        List<Reservation> toutesReservations =reservationService.findAllAValider();
        return new ResponseEntity<>(reservationMapper.toDto(toutesReservations), HttpStatus.OK);
    }

    /* controller pour avoir toutes les reservations en attente*/
    @RequestMapping(path ="/EnCours",method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO> >listOfReservationsEnCours() {
        List<Reservation> toutesReservations =reservationService.findAllEnCours();
        return new ResponseEntity<>(reservationMapper.toDto(toutesReservations), HttpStatus.OK);
    }

    /* controller pour verifier la validité du délai de location d'une réservation*/
    @RequestMapping(path = "/verifierReservation",method = RequestMethod.PUT,produces = "application/json")
    public ResponseEntity<ReservationDTO> verifierReservation(@RequestBody ReservationDTO reservationAVerifierDTO){
        Reservation reservationAVerifier = reservationMapper.toEntity(reservationAVerifierDTO);
        ReservationDTO dto =reservationMapper.toDto(reservationService.verifierEtatReservation(reservationAVerifier));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /* controller pour avoir toutes les reservations en attente*/
    @RequestMapping(path ="/byUser",method = RequestMethod.POST)
    public ResponseEntity<List<ReservationDTO> >listOfReservationsByUser(@RequestBody UserDTO userDTO) {
        User user =userMapper.toEntity(userDTO);
        List<Reservation> toutesReservationsByUserEnCours =reservationService.findAllByUser(user);
        return new ResponseEntity<>(reservationMapper.toDto(toutesReservationsByUserEnCours), HttpStatus.OK);
    }

    /* controller pour avoir toutes les reservations à relancer 1 fois qui sont à traiter par le batch*/
    @RequestMapping(path ="/all/batch",method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO> >listOfReservationsForBatch() {
        List<Reservation> toutesReservations =reservationService.findAll();
        logger.info(" taille de toutes les réservations : "+toutesReservations.size());
        List<Reservation> reservationsBatch =new ArrayList<>();
        if (!toutesReservations.isEmpty()){
            for (Reservation reservation:toutesReservations){
                if (!reservationService.verifierDateDeRetrait(reservation)&&(!reservation.getRelance())){
                    reservationsBatch.add(reservation);
                }
            }
        }
        logger.info(" taille de la liste les réservations pour batch : "+reservationsBatch.size());
        return new ResponseEntity<>(reservationMapper.toDto(reservationsBatch), HttpStatus.OK);
    }

    /* controller pour verifier qu'un user a une reservation d'un livre*/

    /*@RequestMapping(path="/verifierUserLivre",method = RequestMethod.PUT,produces = "application/json")
    public Boolean verifierReservationByUserByLivre (@RequestBody ReservationDTO reservationModifieDTO){
        User user = userMapper.toEntity(reservationModifieDTO.getUser());
        Livre livre =livreMapper.toEntity(reservationModifieDTO.getLivre());
        Boolean reservationTrouvee=reservationService.verifierReservationByUserBylivre(user,livre);
        return reservationTrouvee;
    }

     */

    /* controller pour vérifier le retrait d'une réservation < 48h*/
    @RequestMapping(path = "/verifierRetrait",method = RequestMethod.PUT,produces = "application/json")
    public Boolean verifierRetraitReservation(@RequestBody ReservationDTO reservationModifieDTO) {
        Reservation reservationAVerifier = reservationMapper.toEntity(reservationModifieDTO);
        Boolean result=reservationService.verifierRetraitReservationApresMail(reservationAVerifier);
        return result;
    }


    /* controller pour vérifier que le user n'a pas de reservation du livre*/
    @RequestMapping(path ="/verifierPossessionLivreForUser/{idLivre}&&{idUser}",method = RequestMethod.GET)
    public Boolean getVerifierPossessionLivreForUser(@PathVariable int idLivre,@PathVariable int idUser) throws RecordNotFoundException {
        Livre livre = livreService.findById(idLivre);
        User user = userService.getUserById(idUser);
        Boolean result=reservationService.verifierPossessionLivreForUser(livre,user);
        return result;
    }

    /* controller pour avoir toutes les reservations pour informer par mail du retrait de la date limite avant annulation*/
    @RequestMapping(path ="/all/infoReservation",method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO> >listOfReservationsForInfoReservation() {
        List<Reservation> reservationsAInformer =reservationService.findAllReservationByEtatReservationAndByDateMail();
        logger.info(" taille de reservationsainformer: "+reservationsAInformer.size());
        return new ResponseEntity<>(reservationMapper.toDto(reservationsAInformer), HttpStatus.OK);
    }

    /* controller pour avoir toutes les reservations pour informer par mail que la date de retrait est dépassée et l'annulation de la réservation*/
    @RequestMapping(path ="/all/dateRetraitNull",method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO> >listOfReservationsWithDateRetraitNull() {
        List<Reservation> reservationsAAnnuler =reservationService.findAllReservationByEtatReservationAndByDateRetrait();
        logger.info(" taille de reservationsAAnnuler: "+reservationsAAnnuler.size());
        return new ResponseEntity<>(reservationMapper.toDto(reservationsAAnnuler), HttpStatus.OK);
    }

}
