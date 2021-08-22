package com.bibliotheque.service;

import com.bibliotheque.entity.Livre;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.form.Search;
import com.bibliotheque.repository.LivreRepository;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LivreService {

    public boolean present;

    Logger logger = (Logger) LoggerFactory.getLogger(LivreService.class);

    @Autowired
    LivreRepository livreRepository;

    /*Methode pour avoir tous les livres de la base de données*/
    public List<Livre> findAll() { return livreRepository.findAll(); }

    /*Methode pour obtenir tous les livres en un seul exemplaire disponibles de la base de données*/
    public List<Livre> getAllExemplairesDisponiblesEnUnSeulExemplaire() {
        List<Livre> result1 = livreRepository.findAll();
        //logger.info(" liste e result1"+result1);
        List<Livre> result2=new ArrayList<Livre>();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 de tous les livres de la BD avec getAllLivresDisponoibles si taille de result1 >0 ");
            present=false;
            logger.info(" taille de result1: "+result1.size());
            result2.add(result1.get(0));
            for (int i=1;i<result1.size();i=i+1){
                //if (result1.get(i).getDisponibilite()) {
                    present=false;
                    for (int j=0;j<result2.size();j=j+1){
                        if (result1.get(i).getTitre().equals(result2.get(j).getTitre())) {
                            present=true;
                        }
                    }
                    if (!present){
                        result2.add(result1.get(i));
                        logger.info(" retour du titre ajouté"+result1.get(i).getTitre());
                    }
                }
            }
        logger.info(" taille de la liste result2 "+result2.size());
            return result2;
        //} else {
            //logger.info(" retour nouvelle liste des Livres Disponibles car pas d'élément dans la liste result1 ");
           /// return result2;
        //}
    }


    /*Methode pour trouver par son id un livre dans la base de données*/
    public Livre findById(int id) {
        return  livreRepository.findById(id).get();
    }

    /*Methode pour obtenir tous les Livres disponibles de la base de données*/
    public List<Livre> getAllLivresDisponibles() {
        List<Livre> result1 = livreRepository.findAll();
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

    /*Methode pour avoir tous les exemplaires d'un livre*/
    public List<Livre> getAllExemplairesDUnLivre(int id){
        Livre livreRecherche=findById(id);
        List<Livre> resultExemplaires=livreRepository.findAllByTitre(livreRecherche.getTitre());
        if (resultExemplaires.size()>0){
            return resultExemplaires;
        } else{
            return new ArrayList<Livre>();
        }
    }



    /*Methode pour avoir tous les exemplaires disponibles d'un livre*/
    public List<Livre> getAllExemplairesDidponiblesDUnLivre(int id){
        Livre livreRecherche=findById(id);
        List<Livre> result1=livreRepository.findAllByTitre(livreRecherche.getTitre());
        List<Livre> result2=new ArrayList<Livre>();
        if (result1.size()>0){
            logger.info(" retour liste result1 de tous les exemplaires de la BD avec getAllExemplairesDisponoiblesDUnLivre si taille de result1 >0 ");
            for (int i=0;i<result1.size();i=i+1){
                if (result1.get(i).getDisponibilite()) {
                    result2.add(result1.get(i));
                }
            }
            return result2;
        } else{
            logger.info(" retour nouvelle liste des exemplaires Disponibles car pas d'élément dans la liste result1 ");
            return result2;
        }
    }

    /*Methode pour sauvegarder un livre dans la base de données*/
    public void save(Livre livre) {
        logger.info(" l'id de newLivre vaut: "+livre.getIdLivre());
        livreRepository.save(livre);
    }

    /*Methode pour creer un livre dans la base de données*/
    public Livre createLivre(Livre entity) throws RecordNotFoundException {
        logger.info(" retour de la valeur de l'entité entity de CreateLivre" + entity);
        entity.setDisponibilite(true);
        //enregistrement du livre dans la basse de données
        logger.info(" retour de l'entité newLivre de createLivre qui a été créée et sauvegardée");
        return livreRepository.save(entity);
    }


    /*Methode pour modifier un livre dans la base de données*/
    public Livre updateLivre(Livre entity) throws RecordNotFoundException {
        Livre livreAModifier = findById(entity.getIdLivre());
        if(livreAModifier!=null) {
            logger.info(" l'entité livre à modifier a été trouvée et peut être modifiée");
            logger.info(" retour de la nouvelle entité site de UpdateLivre qui a été sauvegardée et le livreAModifier était existant");
            return livreRepository.save(entity);
        } else {
            throw new RecordNotFoundException("Pas de livre trouvé avec l'id de l'entité et il ne peut être modifié");
        }
    }

    /*Methode pour effacer un livre dans la base de données*/
    public void deleteLivreById(int id) throws RecordNotFoundException {
        Optional<Livre> livreAEffacer = livreRepository.findById(id);
        if(livreAEffacer.isPresent()) {
            Livre livreTrouve = livreAEffacer.get();
            livreRepository.deleteById(livreTrouve.getIdLivre());
        } else {
            throw new RecordNotFoundException("Pas de livre enregistré avec cet Id");
        }
    }

    /*Methode pour une recherche de livres*/
    public List<Livre> getAllLivresBySearch(Search search){
        //return livreRepository.findAllLivresByTitreOrAuteurOrNomCategorie(search.getTitre(),search.getAuteur(),search.getNomCategorie());
        //return livreRepository.findAllLivresByTitreStartsWithOrAuteurOrNomCategorie(search.getTitre(),search.getAuteur(),search.getNomCategorie());
    List<Livre> livreTotal = new ArrayList<>();
    logger.info(" valeur de titre "+ search.getTitre());
    logger.info(" valeur de auteur "+ search.getAuteur());
    logger.info(" valeur de nom catégorie "+ search.getNomCategorie());
    if ((!search.getTitre().equals(""))&&(search.getAuteur().equals(""))&&(search.getNomCategorie().equals(""))){
        livreTotal.addAll(livreRepository.findAllLivresByTitreStartsWith(search.getTitre()));
        }
    if ((search.getTitre().equals(""))&&(!search.getAuteur().equals(""))&&(search.getNomCategorie().equals(""))){
        livreTotal.addAll(livreRepository.findAllLivresByAuteurStartsWith(search.getAuteur()));
        }
    if ((search.getTitre().equals(""))&&(search.getAuteur().equals(""))&&(!search.getNomCategorie().equals(""))){
            livreTotal.addAll(livreRepository.findAllLivresByNomCategorie(search.getNomCategorie()));
        }
    if ((!search.getTitre().equals(""))&&!(search.getAuteur().equals(""))&&(search.getNomCategorie().equals(""))){
            livreTotal.addAll(livreRepository.findAllLivresByTitreStartsWithAndAuteurStartsWith(search.getTitre(),search.getAuteur()));
        }
    if ((!search.getTitre().equals(""))&&(search.getAuteur().equals(""))&&!(search.getNomCategorie().equals(""))){
            livreTotal.addAll(livreRepository.findAllLivresByTitreStartsWithAndNomCategorie(search.getTitre(),search.getNomCategorie()));
        }
    if ((search.getTitre().equals(""))&&!(search.getAuteur().equals(""))&&!(search.getNomCategorie().equals(""))){
            livreTotal.addAll(livreRepository.findAllLivresByAuteurStartsWithAndNomCategorie(search.getAuteur(),search.getNomCategorie()));
        }
    return livreTotal;
    }


    /*Methode pour obtenir tous les exemplaires d'un livre par titre */
    public List<Livre> getAllLivresByTitre(String titre) throws RecordNotFoundException {
        List<Livre> listeLivresTrouves = livreRepository.findAllByTitre(titre);
        if (listeLivresTrouves.size() > 0) {
            return listeLivresTrouves;
        } else {
            return null;
        }
    }

    /*Methode pour obtenir tous les exemplaires disponibles d'un livre par titre */
    public List<Livre> getLivreDisponibleByTitre(String titre) throws RecordNotFoundException {
        List<Livre> listeLivresTrouves = livreRepository.findAllByTitre(titre);
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
}
