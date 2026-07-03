package com.spring.security.service;

import com.spring.security.dto.UserLoginDto;
import com.spring.security.dto.UserResponseDto;
import com.spring.security.dto.UserSignupDto;
import com.spring.security.exception.UserExistException;
import com.spring.security.exception.UserNotFoundException;
import com.spring.security.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import com.spring.security.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class UserServImpl implements UserServ{
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponseDto saveUser(UserSignupDto userSignupDto) throws UserExistException {
        if(userRepo.findByEmail(userSignupDto.getEmail()).isPresent()){
            throw new UserExistException("User with email "+userSignupDto.getEmail()+" already exists");
        }
        else {
            User user = modelMapper.map(userSignupDto,User.class);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(null);
            user.setPassword(passwordEncoder.encode(userSignupDto.getPassword()));
          return modelMapper.map(userRepo.save(user),UserResponseDto.class);
        }

    }

    @Override
    public UserResponseDto login(UserLoginDto userLoginDto) throws UserNotFoundException {
        User user = userRepo.findByUsername(userLoginDto.getUsername()).orElseThrow(() -> new UserNotFoundException("User with username " + userLoginDto.getUsername() + " not found"));
        if(!user.getPassword().equals(userLoginDto.getPassword())){
            throw new UserNotFoundException("Invalid password");
        }
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateuser(int id,UserSignupDto UserSignupDto) throws UserNotFoundException {
        User user = userRepo.findByUsername(UserSignupDto.getUsername()).orElseThrow(() -> new UserNotFoundException("User with username " + UserSignupDto.getUsername() + " not found"));
        user.setPassword(UserSignupDto.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(UserSignupDto.getRole());
        user.setGender(UserSignupDto.getGender());
        return modelMapper.map(userRepo.save(user), UserResponseDto.class);
    }

    @Override
    public void deleteuser(String username) throws UserNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        userRepo.delete(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) throws UserNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserById(int id) throws UserNotFoundException {
        return modelMapper.map(userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found")), UserResponseDto.class);
    }


}



