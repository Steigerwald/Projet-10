package com.projet7.BibliothequeVille.repository;

import com.projet7.BibliothequeVille.entity.Livre;
import com.projet7.BibliothequeVille.entity.User;
import com.projet7.BibliothequeVille.form.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    List<Livre> findByTitre(String titre);

    List<Livre> findAllLivresByTitreOrAuteurOrNomCategorie(String titre,String auteur, String nomCategorie);
    //List<Livre> findAllLivresByTitre(String titre);
    //List<Livre> findAllLivresByAuteur(String auteur);
    //List<Livre> findAllLivresByNomCategorie(String nomCategorie);
}
