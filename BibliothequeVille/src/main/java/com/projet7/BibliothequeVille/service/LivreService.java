package com.projet7.BibliothequeVille.service;

import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.form.Search;
import com.projet7.BibliothequeVille.repository.LivreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService {

    Logger logger = (Logger) LoggerFactory.getLogger(LivreService.class);

    @Autowired
    public LivreRepository livreRepository;


    @Autowired
    public BibliothequeService bibliothequeService;

    /*Methode pour obtenir tous les livres de la base de données*/
    public List<Livre> getAllLivres() {
        List<Livre> result1 =(List<Livre>) livreRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 de tous les livres de la BD avec getAllLivres si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Livre>();
        }
    }

    /*Methode pour obtenir tous les Livres disponibles de la base de données*/
    public List<Livre> getAllLivresDisponibles() {
        List<Livre> result1 =(List<Livre>) livreRepository.findAll();
        List<Livre> result2=new ArrayList<Livre>();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 de tous les livres de la BD avec getAllLivresDisponoibles si taille de result1 >0 ");
            for (int i=0;i<result1.size();i=i+1){
                if (result1.get(i).getDisponibilite()) {
                    result2.add(result1.get(i));
                }
            }
            return result2;
        } else {
            logger.info(" retour nouvelle liste des Livres Disponibles car pas d'élément dans la liste result1 ");
            return result2;
        }
    }


    /*Methode pour obtenir un livre par Id de la base de données*/
    public Livre getLivreById(Long id) throws RecordNotFoundException {
        Optional<Livre> livreTrouve = livreRepository.findById(id);
        if(livreTrouve.isPresent()) {
            logger.info(" retour du livre car il est présent ");
            return livreTrouve.get();
        } else {
            throw new RecordNotFoundException("Pas de livre enregistré avec cet Id");
        }
    }


    /*Methode pour obtenir tous les exemplaires disponibles d'un livre par titre */
    public List getLivreDisponibleByTitre(String titre) throws RecordNotFoundException {
        List<Livre> listeLivresTrouves = livreRepository.findByTitre(titre);
        List<Livre> livresDisponibles =new ArrayList<>();
        if(listeLivresTrouves.size()>0) {
            logger.info(" retour du livre car la liste des livre trouvé n'est pas vide ");
            for (int i=0;i<listeLivresTrouves.size();i++){
                if (listeLivresTrouves.get(i).getDisponibilite()){
                    livresDisponibles.add(listeLivresTrouves.get(i));
                }
            }
            return livresDisponibles;
            // attention à prévoir le cas où livresDisponibles est null lorsque tous les exemplaires sont loués
        } else {
            throw new RecordNotFoundException("Pas de livre enregistré avec cet Id");
        }
    }


    /*Methode pour creer un livre dans la base de données*/
    public Livre CreateLivre(Livre entity) throws RecordNotFoundException {
        Livre newLivre = new Livre();
        logger.info(" retour de la valeur de l'entité entity de CreateLivre"+entity);
        newLivre.setTitre(entity.getTitre());
        newLivre.setDescription(entity.getDescription());
        newLivre.setAuteur(entity.getAuteur());
        newLivre.setNombrePages(entity.getNombrePages());
        newLivre.setDisponibilite(true);
        newLivre.setNomCategorie(entity.getNomCategorie());
        newLivre.setEtatLivre(entity.getEtatLivre());
        newLivre.setDateAchat(entity.getDateAchat());
        newLivre.setPrixLocation(entity.getPrixLocation());
        //enregistrement du livre dans la basse de données
        entity = livreRepository.save(newLivre);
        logger.info(" retour de l'entité newLivre de createLivre qui a été créée et sauvegardée");
        return entity;
    }



    /*Methode pour modifier un livre dans la base de données*/
    public Livre updateLivre(Livre entity) throws RecordNotFoundException {
        Livre livreAModifier = getLivreById(entity.getIdLivre());
        if(livreAModifier!=null) {
            logger.info(" l'entité livre à modifier a été trouvée et peut être modifiée");
            livreAModifier.setTitre(entity.getTitre());
            livreAModifier.setDescription(entity.getDescription());
            livreAModifier.setAuteur(entity.getAuteur());
            livreAModifier.setNombrePages(entity.getNombrePages());
            livreAModifier.setDisponibilite(entity.getDisponibilite());
            livreAModifier.setNomCategorie(entity.getNomCategorie());
            livreAModifier.setEtatLivre(entity.getEtatLivre());
            livreAModifier.setDateAchat(entity.getDateAchat());
            livreAModifier.setPrixLocation(entity.getPrixLocation());
            livreAModifier.setBibliotheque(entity.getBibliotheque());
            livreAModifier.setReservation(entity.getReservation());

            livreAModifier=livreRepository.save(livreAModifier);
            logger.info(" retour de la nouvelle entité site de UpdateLivre qui a été sauvegardée et le livreAModifier était existant");
            return livreAModifier;
        } else {
            throw new RecordNotFoundException("Pas de livre trouvé avec l'id de l'entité et il ne peut être modifié");
        }
    }

    /*Methode pour effacer un livre dans la base de données*/
    public void deleteLivreById(Long id) throws RecordNotFoundException {
        Optional<Livre> livreAEffacer = livreRepository.findById(id);
        if(livreAEffacer.isPresent()) {
            Livre livreTrouve = livreAEffacer.get();
           livreTrouve.setBibliotheque(null);
           updateLivre(livreTrouve);
            livreRepository.deleteById(livreTrouve.getIdLivre());
        } else {
            throw new RecordNotFoundException("Pas de livre enregistré avec cet Id");
        }
    }

   //Methode pour une recherche de livres
    public List<Livre> getAllLivresBySearch(Search search){
        List<Livre> listLivresTrouvesDeRecherche=livreRepository.findAllLivresByTitreOrAuteurOrNomCategorie(search.getTitre(),search.getAuteur(),search.getNomCategorie());
        return listLivresTrouvesDeRecherche;
    }


    /*Methode pour checher un livre dans la base de données*/
/*
    public List<Livre> getAllLivresByTitre(Search search){
        List<Livre> listLivresTrouvesDeRecherche=livreRepository.findAllLivresByTitre(search.getTitre());
        return listLivresTrouvesDeRecherche;
    }
*/
}
