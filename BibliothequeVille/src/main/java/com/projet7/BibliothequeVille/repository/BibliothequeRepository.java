package com.projet7.BibliothequeVille.repository;

import com.projet7.BibliothequeVille.entity.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliothequeRepository extends JpaRepository<Bibliotheque, Long> {
}
