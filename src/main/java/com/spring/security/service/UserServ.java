package com.spring.security.service;

import com.spring.security.dto.UserAuthResponseDto;
import com.spring.security.dto.UserLoginDto;
import com.spring.security.dto.UserResponseDto;
import com.spring.security.dto.UserSignupDto;
import com.spring.security.exception.UserExistException;
import com.spring.security.exception.UserNotFoundException;

import java.util.List;

public interface UserServ {
    UserResponseDto saveUser(UserSignupDto userSignupDto) throws UserExistException;
    UserResponseDto login(UserLoginDto userLoginDto) throws UserNotFoundException;
    UserResponseDto updateuser(int id,UserSignupDto UserSignupDto) throws UserNotFoundException;
    void deleteuser(String email) throws UserNotFoundException;
    UserResponseDto getUserByEmail(String email) throws UserNotFoundException;
    UserResponseDto getUserById(int id) throws UserNotFoundException;
    UserAuthResponseDto getUserByUsername(String username) throws UserNotFoundException;
    List<UserResponseDto> getAllUsers();
}
