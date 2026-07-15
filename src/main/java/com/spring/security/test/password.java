package com.spring.security.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class password {
    public static PasswordEncoder passwordEncode=new BCryptPasswordEncoder();
    public static void main(String[] args) {
        String password;
        password="pranay@123";
        String passwordencoded=passwordEncode.encode(password);
        System.out.println("original password : "+password);
        System.out.println("encoded password : "+passwordencoded);
    }
}
