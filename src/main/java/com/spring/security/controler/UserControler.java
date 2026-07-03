package com.spring.security.controler;

import com.spring.security.dto.UserLoginDto;
import com.spring.security.dto.UserResponseDto;
import com.spring.security.dto.UserSignupDto;
import com.spring.security.service.UserServImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserControler {
    private final UserServImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestParam int id, @RequestBody UserSignupDto userSignupDto) {
        UserResponseDto updatedUser = userService.updateuser(id, userSignupDto);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/delete")
    public void deleteuser(@RequestParam String username){
        userService.deleteuser(username);
    }



}
