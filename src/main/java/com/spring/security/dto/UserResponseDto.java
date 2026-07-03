package com.spring.security.dto;

import com.spring.security.enums.Gender;
import com.spring.security.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private Gender gender;
    private Role role;
    private LocalDateTime updatedAt;

}
