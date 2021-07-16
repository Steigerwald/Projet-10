package com.projet7.BibliothequeVille.repository;

import com.projet7.BibliothequeVille.entity.Role;
import com.projet7.BibliothequeVille.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByIdRole(Long id);
}
