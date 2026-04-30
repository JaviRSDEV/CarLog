package com.carlog.backend.repository;

import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, String> {
    Optional<User> findByDni(String dni);
    Optional<User> findByName(String name);
    @Transactional
    void deleteByDni(String dni);
    Optional<User> findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);
    Optional<User> findFirstByWorkshopAndRole(Workshop workshop, Role role);
}
