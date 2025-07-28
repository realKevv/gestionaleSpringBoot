package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.User;
import com.kevv.gestionale.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/test")
    List<User> all() {
        return userRepository.findAll();
    }
}

