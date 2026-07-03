package com.spring.security.repository;

import com.spring.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Override
    Optional<User> findById(Integer integer);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
