package com.bibliotheque.repository;

import com.bibliotheque.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByMailUser(String mail);
    Optional<User> findByIdUser(int id);

}
