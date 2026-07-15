package com.spring.security.service;

import com.spring.security.dto.UserAuthResponseDto;
import com.spring.security.dto.UserLoginDto;
import com.spring.security.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthServImpl implements AuthServ {
    private final userServImpl userServ;;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    @Override
    public UserAuthResponseDto registerUser(UserSignupDto userSignupDto) {
        if(userServ.getUserByEmail(userSignupDto.getEmail()) != null){
            throw new RuntimeException("User already exists");
        }

        return modelMapper.map(userServ.saveUser(userSignupDto),UserAuthResponseDto.class);
    }

    @Override
    public UserAuthResponseDto loginuser(UserLoginDto userLoginDto) {
        UserAuthResponseDto userResponseDto = userServ.getUserByUsername(userLoginDto.getUsername());
        if(userResponseDto == null){
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(userLoginDto.getPassword(), userResponseDto.getPassword())){
            throw new RuntimeException("Invalid password");
        }
//        if(!matches(userLoginDto.getPassword(), userResponseDto.getPassword())){
//            throw new RuntimeException("Invalid password");
//        }
        return modelMapper.map(userServ.login(userLoginDto), UserAuthResponseDto.class);
    }
}
