package com.spring.security.service;

import com.spring.security.dto.UserAuthResponseDto;
import com.spring.security.dto.UserLoginDto;
import com.spring.security.dto.UserResponseDto;
import com.spring.security.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthServImpl implements AuthServ {
    private final UserServImpl userServ;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponseDto registerUser(UserSignupDto userSignupDto) {
        if(userServ.getUserByEmail(userSignupDto.getEmail()) != null){
            throw new RuntimeException("User already exists");
        }
        return userServ.saveUser(userSignupDto);
    }

    @Override
    public UserResponseDto loginuser(UserLoginDto userLoginDto) {
        UserAuthResponseDto userResponseDto = userServ.getUserByUsername(userLoginDto.getUsername());
        if(userResponseDto == null){
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(userLoginDto.getPassword(), userResponseDto.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return userServ.login(userLoginDto);
    }
}
