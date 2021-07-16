package com.projet7.BibliothequeVille.controller;

import com.projet7.BibliothequeVille.entity.Reservation;
import com.projet7.BibliothequeVille.entity.dto.ReservationDTO;
import com.projet7.BibliothequeVille.entity.mapper.ReservationMapper;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    public ReservationService reservationService;

    public ReservationMapper reservationMapper;


    public ReservationController(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    /* controller pour afficher les réservations */
    @RequestMapping(method = RequestMethod.GET)
    public List<Reservation> getAllReservations(Model model, Principal principal) {
        List<Reservation> listReservations = reservationService.getAllReservations();
        return listReservations;
    }

    /* Controller pour afficher une réservation par Id */
    @RequestMapping(path="/reservation/{id}", method = RequestMethod.GET)
    public Reservation getReservationById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Reservation reservationTrouve = reservationService.getReservationById(id);
        if (reservationTrouve!=null){
            return reservationTrouve;
        }else{
            return new Reservation();
        }
    }

    /* controller pour effacer une réservation */
    @RequestMapping(path = "/deleteReservation/{id}",method = RequestMethod.POST)
    public void deleteReservationById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException{
        reservationService.deleteReservationById(id);
    }

    /* controller pour annuler une réservation */
    @RequestMapping(path = "/annulerReservation/{id}",method = RequestMethod.POST)
    public void annulerReservationById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException{
        Reservation reservationTrouve = reservationService.getReservationById(id);
        reservationService.annulerReservation(reservationTrouve);
    }

    /* controller pour creer une réservation */
    @RequestMapping(path = "/createReservation",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) throws RecordNotFoundException, ParseException {

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation reservationCree =reservationService.CreateReservation(reservation);
        logger.info(" la réservation comporte les éléments suivants : "+reservationCree);
        return reservationMapper.toDto(reservationCree);
    }

    /* controller pour modifier une réservation */
    @RequestMapping(path = "/updateReservation",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public ReservationDTO updateReservation(@RequestBody ReservationDTO reservationModifieDTO) throws RecordNotFoundException, ParseException {
        Reservation reservationModifie = reservationMapper.toEntity(reservationModifieDTO);
        Reservation reservation=reservationService.UpdateReservation(reservationModifie);
        return reservationMapper.toDto(reservation);
    }
}
