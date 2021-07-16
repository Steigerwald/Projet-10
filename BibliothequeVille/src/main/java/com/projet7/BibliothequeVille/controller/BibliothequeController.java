package com.projet7.BibliothequeVille.controller;

import com.projet7.BibliothequeVille.entity.Bibliotheque;
import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.dto.BibliothequeDTO;
import com.projet7.BibliothequeVille.entity.mapper.BibliothequeMapper;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.service.BibliothequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotheques")
public class BibliothequeController {



    @Autowired
    public BibliothequeService bibliothequeService;

    public BibliothequeMapper bibliothequeMapper;

    public BibliothequeController(BibliothequeMapper bibliothequeMapper) {
        this.bibliothequeMapper=bibliothequeMapper;

    }


    /* Controller pour la liste de toutes les bilbiothèques dans la base*/
    @RequestMapping(method = RequestMethod.GET)
    public List<Bibliotheque> getAllBibliotheques() {
        List<Bibliotheque> listBibliotheques = bibliothequeService.getAllBibliotheques();
        return listBibliotheques;
    }


    /* Controller pour afficher une bibliotheque par Id */
    @RequestMapping(path="/bibliotheque/{id}", method = RequestMethod.GET)
    public Bibliotheque getBibliothequeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Bibliotheque bibliothequeTrouve = bibliothequeService.getBibliothequeById(id);
        if (bibliothequeTrouve!=null){
            return bibliothequeTrouve;
        }else{
            return new Bibliotheque();
        }
    }


    /* controller pour creer une bibliothèque */
    @RequestMapping(path = "/addBibliotheque", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public BibliothequeDTO addBibliotheque (@RequestBody BibliothequeDTO bibliothequeDTO) throws RecordNotFoundException {
        Bibliotheque bibliotheque= bibliothequeMapper.DTOTOBibliotheque(bibliothequeDTO);
        Bibliotheque bibliothequeCree =bibliothequeService.createBibliotheque(bibliotheque);
        return bibliothequeMapper.bibliothequeToDTO(bibliothequeCree);
    }


    /* controller pour modifier une bibliotheque */
    @RequestMapping(path = "/updateBibliotheque",method = RequestMethod.POST,produces = "application/json")
    public BibliothequeDTO updateBibliotheque(@RequestBody BibliothequeDTO bibliothequeModifieDTO) throws RecordNotFoundException {

        Bibliotheque bibliothequeModifie= bibliothequeMapper.DTOTOBibliotheque(bibliothequeModifieDTO);
        Bibliotheque bibliotheque=bibliothequeService.updateBibliotheque(bibliothequeModifie);
        return bibliothequeMapper.bibliothequeToDTO(bibliotheque);
    }


    /* controller pour effacer une bibliotheque de la base de données */
    @RequestMapping(path = "/deleteBibliotheque/{id}",method = RequestMethod.POST)
    public void deleteBibliothequeById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException{
        bibliothequeService.deleteBibliothequeById(id);
    }


}
