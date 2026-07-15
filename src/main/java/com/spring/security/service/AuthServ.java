package com.spring.security.service;

import com.spring.security.dto.UserAuthResponseDto;
import com.spring.security.dto.UserLoginDto;
import com.spring.security.dto.UserResponseDto;
import com.spring.security.dto.UserSignupDto;
import com.spring.security.exception.UserExistException;
import com.spring.security.exception.UserNotFoundException;

public interface AuthServ {
 UserAuthResponseDto registerUser(UserSignupDto userSignupDto) throws UserExistException;
 UserAuthResponseDto loginuser(UserLoginDto userLoginDto) throws UserNotFoundException;

}
