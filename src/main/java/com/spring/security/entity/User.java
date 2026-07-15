package com.spring.security.entity;

import com.spring.security.enums.Gender;
import com.spring.security.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collation = "users")
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private Gender gender;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
