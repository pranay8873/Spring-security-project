package com.spring.security.dto;

import com.spring.security.enums.Gender;
import com.spring.security.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignupDto {
    private String username;
    private String email;
    private String password;

    private Role role;
    private Gender gender;;

}
