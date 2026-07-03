package com.spring.security.controler;

import com.spring.security.dto.UserLoginDto;
import com.spring.security.dto.UserResponseDto;
import com.spring.security.dto.UserSignupDto;
import com.spring.security.service.UserServImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
public class AuthControler {
    private final UserServImpl userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserSignupDto userSignupDto) {
        UserResponseDto registeredUser = userService.saveUser(userSignupDto);
        return ResponseEntity.ok(registeredUser);
    }
    @GetMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestParam UserLoginDto userLoginDto) {
        UserResponseDto loggedInUser = userService.login(userLoginDto);
        return ResponseEntity.ok(loggedInUser);
    }


}
