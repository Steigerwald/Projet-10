package com.bibliotheque.controller;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.entity.dto.LivreDTO;
import com.bibliotheque.entity.dto.SearchDTO;
import com.bibliotheque.entity.mapper.LivreMapper;
import com.bibliotheque.entity.mapper.SearchMapper;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.form.Search;
import com.bibliotheque.service.LivreService;
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
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    LivreService livreService;

    @Autowired
    LivreMapper livreMapper;

    @Autowired
    SearchMapper searchMapper;

    Logger logger = (Logger) LoggerFactory.getLogger(LivreController.class);



    /* controller pour avoir tous les livres par exemplaire*/
    @RequestMapping(path ="/",method = RequestMethod.GET)
    public ResponseEntity<List<LivreDTO> >listOfBooks() {
        List<Livre> tousLivres=livreService.getAllExemplairesDisponiblesEnUnSeulExemplaire();
        return new ResponseEntity<>(livreMapper.toDto(tousLivres), HttpStatus.OK);
    }

    /* controller pour avoir tous les livres mais en gardant un seul exemplaire*/
    @RequestMapping(path ="/LivresParTitre",method = RequestMethod.GET)
    public ResponseEntity<List<LivreDTO> >listOfBooksByTitre() {
        List<Livre> tousLivres=livreService.findAll();
        return new ResponseEntity<>(livreMapper.toDto(tousLivres), HttpStatus.OK);
    }


    /* controller pour obtenir un livre par id */
    @RequestMapping(path="/{id}",method = RequestMethod.GET)
    public ResponseEntity <LivreDTO> bookId(@PathVariable int id) {
        Livre leLivre =livreService.findById(id);
        if (leLivre==null){
            return new ResponseEntity<>(livreMapper.toDto(leLivre), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(livreMapper.toDto(leLivre), HttpStatus.OK);
        }
    }

    /* Controller pour la liste de tous les livres disponibles */
    @RequestMapping(path ="/disponibles",method = RequestMethod.GET)
    public ResponseEntity <List<LivreDTO>> getAllLivresDisponibles() {
        List<Livre> LivresDispo = livreService.getAllLivresDisponibles();
        return new ResponseEntity<>(livreMapper.toDto(LivresDispo), HttpStatus.OK);
    }

    /* Controller pour la liste de tous les exemplaires d'un livre */
    @RequestMapping(path ="/allExemplaires/{id}",method = RequestMethod.GET)
    public ResponseEntity <List<LivreDTO>> getAllExemplaires(@PathVariable int id) {
        List<Livre> exemplaires = livreService.getAllExemplairesDUnLivre(id);
        return new ResponseEntity<>(livreMapper.toDto(exemplaires), HttpStatus.OK);
    }

    /* Controller pour la liste de tous les exemplaires disponibles d'un livre */
    @RequestMapping(path ="/allExemplairesDisponibles/{id}",method = RequestMethod.GET)
    public ResponseEntity <List<LivreDTO>> getAllExemplairesDisponibles(@PathVariable int id) {
        List<Livre> exemplaires = livreService.getAllExemplairesDidponiblesDUnLivre(id);
        return new ResponseEntity<>(livreMapper.toDto(exemplaires), HttpStatus.OK);
    }


    /* controller pour ajouter un livre */
    @RequestMapping(path = "/addLivre",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<LivreDTO> newBook(@RequestBody LivreDTO livreDTO) throws RecordNotFoundException {
        System.out.println("livre => " + livreMapper.toEntity(livreDTO));
        Livre livre =livreService.createLivre(livreMapper.toEntity(livreDTO));
        return new ResponseEntity<>(livreMapper.toDto(livre), HttpStatus.OK);
    }

    /* controller pour modifier un livre */
    @RequestMapping(path = "/",method = RequestMethod.PUT,produces = "application/json")
    public ResponseEntity<LivreDTO> updateLivre(@RequestBody LivreDTO livreModifieDTO) throws RecordNotFoundException {
        Livre livreModifie = livreMapper.toEntity(livreModifieDTO);
        Livre livre=livreService.updateLivre(livreModifie);
        LivreDTO dto = livreMapper.toDto(livre);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /* controller pour effacer un livre de la base de données */
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void deleteLivreById(Model model, @PathVariable("id") int id) throws RecordNotFoundException{
        livreService.deleteLivreById(id);
    }

    /* Controller pour chercher un livre par titre ou par auteur ou par nom de categorie */
    @RequestMapping(path = "/search", method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<List<LivreDTO> >searchLivresByTitreOrByAuteurOrByNomCategorie(@RequestBody SearchDTO searchDTO) {
        Search search=searchMapper.toEntity(searchDTO);
        List<Livre> listLivresTrouves = livreService.getAllLivresBySearch(search);
        logger.info(" taille de la liste de search : "+listLivresTrouves.size());
        return new ResponseEntity<>(livreMapper.toDto(listLivresTrouves),HttpStatus.OK);
    }

    /* Controller pour chercher un livre par titre disponible et tous les exemplaires */
    @RequestMapping(path = "/exemplairesDisponibles", method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<List<LivreDTO> >searchExemplairesDisponibles(@RequestBody SearchDTO searchDTO) throws RecordNotFoundException {
        Search search=searchMapper.toEntity(searchDTO);
        List<Livre> listLivresTrouves =livreService.getLivreDisponibleByTitre(search.getTitre());
        if (listLivresTrouves.size()==0){
            return new ResponseEntity<>(livreMapper.toDto(listLivresTrouves),HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(livreMapper.toDto(listLivresTrouves),HttpStatus.OK);
        }
    }


}
