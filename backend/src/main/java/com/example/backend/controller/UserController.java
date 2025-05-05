package com.example.backend.controller;

import com.example.backend.DTO.UserDTO;
import com.example.backend.moder.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService service;
    @PostMapping("/register/dang-ki")
    public String register(@RequestBody UserDTO dto) {
       service.register(dto);
        return "User registered successfully";
    }
}
