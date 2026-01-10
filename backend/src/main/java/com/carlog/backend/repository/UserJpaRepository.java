package com.carlog.backend.repository;

import com.carlog.backend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByDni(String dni);
    Optional<User> findByName(String name);
    @Transactional
    void deleteByDni(String dni);
    Optional<User> findFirstByEmail(String email);
}
