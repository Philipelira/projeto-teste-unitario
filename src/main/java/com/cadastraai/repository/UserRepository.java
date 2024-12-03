package com.cadastraai.repository;

import com.cadastraai.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existeEmail(String email);
    Optional<User> encontrarEmail(String email);
}

