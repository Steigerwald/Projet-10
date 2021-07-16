package com.bibliotheque.service;

import com.bibliotheque.entity.Bibliotheque;
import com.bibliotheque.exception.RecordNotFoundException;
import com.bibliotheque.repository.BibliothequeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliothequeService {

    Logger logger = (Logger) LoggerFactory.getLogger(BibliothequeService.class);

    @Autowired
    BibliothequeRepository bibliothequeRepository;

    /*Methode pour avoir toutes les bibliotheques de la base de données*/
    public List<Bibliotheque> findAll() {
        return bibliothequeRepository.findAll();
    }

    /*Methode pour trouver par son id une bibliotheque dans la base de données*/
    public Bibliotheque findById(int id) {
        return  bibliothequeRepository.findById(id).get();
    }

    /*Methode pour sauvegarder une bibliotheque dans la base de données*/
    public void save(Bibliotheque bibliotheque) {
        logger.info(" l'id de newLivre vaut: "+bibliotheque.getIdBibliotheque());
        bibliothequeRepository.save(bibliotheque);
    }


    /*Methode pour creer une bibliotheque dans la base de données*/
    public Bibliotheque createBibliotheque(Bibliotheque entity) throws RecordNotFoundException {
        //enregistrement de la bibliotheque dans la basse de données
        logger.info(" retour de l'entité newBibliotheque de createBibliotheque qui a été créée et sauvegardée");
        return bibliothequeRepository.save(entity);
    }

    /*Methode pour modifier une bibliotheque dans la base de données*/
    public Bibliotheque updateBibliotheque(Bibliotheque entity) throws RecordNotFoundException {
        Bibliotheque bibliothequeAModifier = findById(entity.getIdBibliotheque());
        if (bibliothequeAModifier != null) {
            logger.info(" l'entité bibliotheque à modifier a été trouvée et peut être modifiée et l'Id est: "+bibliothequeAModifier.getIdBibliotheque());
            logger.info(" retour de la nouvelle entité site de updateBibliotheque qui a été sauvegardée et la bibliothequeAModifier était existante");
            if (entity.getLivres()==null) {
                entity.setLivres(bibliothequeAModifier.getLivres());
            }
            return bibliothequeRepository.save(entity);
        } else {
            throw new RecordNotFoundException("Pas de bibliotheque trouvée avec l'id de l'entité et elle ne peut être modifiée");
        }
    }

    /*Methode pour effacer une bibliotheque dans la base de données*/
    public void deleteBibliothequeById(int id) throws RecordNotFoundException {
        Optional<Bibliotheque> bibliothequeAEffacer = bibliothequeRepository.findById(id);
        if(bibliothequeAEffacer.isPresent()) {
            Bibliotheque bibliothequeTrouve = bibliothequeAEffacer.get();
            bibliothequeRepository.deleteById(bibliothequeTrouve.getIdBibliotheque());
        } else {
            throw new RecordNotFoundException("Pas de bibliotheque enregistrée avec cet Id");
        }
    }




}
