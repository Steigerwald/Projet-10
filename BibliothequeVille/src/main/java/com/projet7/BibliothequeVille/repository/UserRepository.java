package com.projet7.BibliothequeVille.repository;

import com.projet7.BibliothequeVille.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByMailUser(String mail);
    Optional<User> findByIdUser(Long id);
}
