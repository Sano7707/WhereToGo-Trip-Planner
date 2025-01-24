package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        if(service.register(user) == null){
            return  ResponseEntity.status(500).body(user);
        }
        return ResponseEntity.status(200).body(user);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Users user) {
        String jwtToken = service.verify(user);
        Map<String,String> response = new HashMap<>();

        if(jwtToken == null){
            return ResponseEntity.status(401).body(response);
        }
        else{
            response = new HashMap<>();
            String username = service.findByEmail(user.getEmail()).getUsername();
            response.put("jwtToken",jwtToken);
            response.put("username",username);
        }
        return ResponseEntity.ok().body(response);
    }
}