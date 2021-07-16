package com.bibliotheque.controller;

import com.bibliotheque.entity.Bibliotheque;
import com.bibliotheque.entity.dto.BibliothequeDTO;
import com.bibliotheque.entity.mapper.BibliothequeMapper;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.service.BibliothequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotheque")
public class BibliothequeController {

    @Autowired
    BibliothequeService bibliothequeService;

    @Autowired
    BibliothequeMapper bibliothequeMapper;

    /* controller pour avoir toutes les bibliotheques*/
    @RequestMapping(path="/",method = RequestMethod.GET)
    public ResponseEntity<List<BibliothequeDTO>> listOfBibliotheques() {
        List<Bibliotheque> toutesBibliotheques= bibliothequeService.findAll();
        return new ResponseEntity<>(bibliothequeMapper.toDto(toutesBibliotheques), HttpStatus.OK);
    }

    /* controller pour obtenir une bibliotheque */
    @RequestMapping(path="/{id}",method = RequestMethod.GET)
    public ResponseEntity<BibliothequeDTO> bibliothequeId(@PathVariable int id) {
        Bibliotheque laBibliotheque= bibliothequeService.findById(id);
        if (laBibliotheque==null){
            return new ResponseEntity<>(bibliothequeMapper.toDto(laBibliotheque), HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(bibliothequeMapper.toDto(laBibliotheque), HttpStatus.OK);
        }
    }

    /* controller pour ajouter une bibliotheque */
    @RequestMapping(path = "/addBibliotheque",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<BibliothequeDTO> newBibliotheque(@RequestBody BibliothequeDTO bibliothequeDTO) throws RecordNotFoundException {
        System.out.println("bibliotheque => " + bibliothequeMapper.toEntity(bibliothequeDTO));
        Bibliotheque bibliotheque =bibliothequeService.createBibliotheque(bibliothequeMapper.toEntity(bibliothequeDTO));
        return new ResponseEntity<>(bibliothequeMapper.toDto(bibliotheque), HttpStatus.OK);
    }

    /* controller pour modifier une bibliotheque */
    @RequestMapping(path = "/",method = RequestMethod.PUT,produces = "application/json")
    public ResponseEntity<BibliothequeDTO> updateBibliotheque(@RequestBody BibliothequeDTO bibliothequeModifieDTO) throws RecordNotFoundException {
        Bibliotheque bibliothequeModifie = bibliothequeMapper.toEntity(bibliothequeModifieDTO);
        Bibliotheque bibliotheque=bibliothequeService.updateBibliotheque(bibliothequeModifie);
        BibliothequeDTO dto = bibliothequeMapper.toDto(bibliotheque);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /* controller pour effacer un livre de la base de données */
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void deleteBibliothequeById(Model model, @PathVariable("id") int id) throws RecordNotFoundException{
        bibliothequeService.deleteBibliothequeById(id);
    }
}
