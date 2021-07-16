package com.projet7.BibliothequeVille.controller;

import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.dto.LivreDTO;
import com.projet7.BibliothequeVille.entity.dto.SearchDTO;
import com.projet7.BibliothequeVille.entity.mapper.LivreMapper;
import com.projet7.BibliothequeVille.entity.mapper.SearchMapper;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.form.Search;
import com.projet7.BibliothequeVille.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/livres")
public class LivreController {


    @Autowired
    public LivreService livreService;

    public LivreMapper livreMapper;

    public SearchMapper searchMapper;


    public LivreController(LivreMapper livreMapper) {
        this.livreMapper = livreMapper;
    }


    /* Controller pour la liste de tous livres dans la base*/
    @RequestMapping(method = RequestMethod.GET)
    public List<Livre> getAllLivres() {
        List<Livre> listLivres = livreService.getAllLivres();
        return listLivres;
    }

    /* Controller pour la liste de tous les livres disponibles */
    @RequestMapping(path ="/disponibles",method = RequestMethod.GET)
    public List<Livre> getAllLivresDisponibles() {
        List<Livre> listLivresDisponibles = livreService.getAllLivresDisponibles();
        return listLivresDisponibles;
    }

    /* Controller pour afficher un livre par Id */
    @RequestMapping(path="/livre/{id}", method = RequestMethod.GET)
    public Livre getLivreById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Livre livreTrouve = livreService.getLivreById(id);
        if (livreTrouve!=null){
            return livreTrouve;
        }else{
            return new Livre();
        }
    }

    /* controller pour effacer un livre de la base de données */
    @RequestMapping(path = "/deleteLivre/{id}",method = RequestMethod.POST)
    public void deleteLivreById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException{
        livreService.deleteLivreById(id);
    }


    /* controller pour l'ajout d'un livre */
    @RequestMapping(path = "/addLivre",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public LivreDTO addLivre(@RequestBody LivreDTO livreDTO) throws RecordNotFoundException, ParseException {
        Livre livre = livreMapper.toEntity(livreDTO);
        Livre livreCree =livreService.CreateLivre(livre);
        return livreMapper.toDto(livreCree);
    }


    /* controller pour modifier un livre */
    @RequestMapping(path = "/updateLivre",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public LivreDTO updateLivre(@RequestBody LivreDTO livreModifieDTO) throws RecordNotFoundException, ParseException {
        Livre livreModifie = livreMapper.toEntity(livreModifieDTO);
        Livre livre=livreService.updateLivre(livreModifie);
        return livreMapper.toDto(livre);
    }

    /* Controller pour chercher un livre par titre ou par auteur ou par nom de categorie */
    @RequestMapping(path = "/searchLivres", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public List<LivreDTO> searchLivresByTitreOrByAuteurOrByNomCategorie(@RequestBody SearchDTO searchDTO) {
        Search search=searchMapper.toEntity(searchDTO);
        List<Livre> listLivresTrouves = livreService.getAllLivresBySearch(search);
        if (listLivresTrouves.size()==0){
           return null;
        } else{
            return livreMapper.toDto(listLivresTrouves);
        }
    }

}
