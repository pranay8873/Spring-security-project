package com.spring.security.repository;

import com.spring.security.entity.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {


    @Override
    Optional<User> findById(@Nonnull String id);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
