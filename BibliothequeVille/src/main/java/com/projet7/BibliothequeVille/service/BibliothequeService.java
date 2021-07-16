package com.projet7.BibliothequeVille.service;

import com.projet7.BibliothequeVille.entity.Bibliotheque;
import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.User;
import com.projet7.BibliothequeVille.exception.RecordNotFoundException;
import com.projet7.BibliothequeVille.repository.BibliothequeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BibliothequeService {


    Logger logger = (Logger) LoggerFactory.getLogger(BibliothequeService.class);

    @Autowired
    public BibliothequeRepository bibliothequeRepository;

    @Autowired
    public LivreService livreService;

    @Autowired
    public UserService userService;


    /*Methode pour obtenir toutes les bibliothèques de la base de données*/
    public List<Bibliotheque> getAllBibliotheques() {
        List<Bibliotheque> result1 =(List<Bibliotheque>) bibliothequeRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 de toutes les bibliotheques de la BD avec getAllBibliotheques si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Bibliotheque>();
        }
    }


    /*Methode pour obtenir une bibliotheque par Id de la base de données*/
    public Bibliotheque getBibliothequeById(Long id) throws RecordNotFoundException {
        Optional<Bibliotheque> bibliothequeTrouve = bibliothequeRepository.findById(id);
        if(bibliothequeTrouve.isPresent()) {
            logger.info(" retour de la bibliotheque car il est présent ");
            return bibliothequeTrouve.get();
        } else {
            throw new RecordNotFoundException("Pas de bibliotheque enregistrée avec cet Id");
        }
    }

    /*Methode pour creer une bibliotheque dans la base de données*/
    public Bibliotheque createBibliotheque(Bibliotheque entity) throws RecordNotFoundException {
        Bibliotheque newBibliotheque = new Bibliotheque();
        newBibliotheque.setNomBibliotheque(entity.getNomBibliotheque());
        newBibliotheque.setLieu(entity.getLieu());
        newBibliotheque.setAdresse(entity.getAdresse());
        newBibliotheque.setLivres(null);
        newBibliotheque.setUsers(null);

        //enregistrement de la bibliotheque dans la basse de données
        entity = bibliothequeRepository.save(newBibliotheque);
        logger.info(" retour de l'entité newBibliotheque de createBibliotheque qui a été créée et sauvegardée");
        return entity;
    }

    /*Methode pour modifier une bibliotheque dans la base de données*/
    public Bibliotheque updateBibliotheque(Bibliotheque entity) throws RecordNotFoundException {
        Bibliotheque bibliothequeAModifier = getBibliothequeById(entity.getIdBibliotheque());
        if (bibliothequeAModifier != null) {
            logger.info(" l'entité bibliotheque à modifier a été trouvée et peut être modifiée");

            bibliothequeAModifier.setNomBibliotheque(entity.getNomBibliotheque());
            bibliothequeAModifier.setLieu(entity.getLieu());
            bibliothequeAModifier.setAdresse(entity.getAdresse());
            //bibliothequeAModifier.setLivres(entity.getLivres());
            //bibliothequeAModifier.setUsers(entity.getUsers());

            bibliothequeAModifier = bibliothequeRepository.save(bibliothequeAModifier);
            logger.info(" retour de la nouvelle entité site de UpdateBibliotheque qui a été sauvegardée et la bibliothequeAModifier était existante");
            return bibliothequeAModifier;
        } else {
            throw new RecordNotFoundException("Pas de bibliotheque trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }

    /*Methode pour effacer une bibliotheque dans la base de données*/
    public void deleteBibliothequeById(Long id) throws RecordNotFoundException {
        Optional<Bibliotheque> bibliothequeAEffacer = bibliothequeRepository.findById(id);
        if(bibliothequeAEffacer.isPresent()) {
            Bibliotheque bibliothequeTrouve = bibliothequeAEffacer.get();
            if (bibliothequeTrouve.getLivres()!=null) {
                for (Livre livre : bibliothequeTrouve.getLivres()) {
                    livre.setBibliotheque(null);
                    livreService.updateLivre(livre);
                }
            }
            bibliothequeTrouve.setLivres(null);

            if (bibliothequeTrouve.getUsers()!=null) {
                for (User userTrouve : bibliothequeTrouve.getUsers()) {
                    userTrouve.setBibliotheques(null);
                    userService.updateUser(userTrouve);
                }
            }
            bibliothequeTrouve.setUsers(null);
            updateBibliotheque(bibliothequeTrouve);
            bibliothequeRepository.deleteById(bibliothequeTrouve.getIdBibliotheque());

        } else {
            throw new RecordNotFoundException("Pas de bibliotheque enregistrée avec cet Id");
        }
    }

}
