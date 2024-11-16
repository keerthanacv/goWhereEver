package com.example.travelPlanner.controller;

import com.example.travelPlanner.service.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginSignUpController {

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/generate-token")
    public ResponseEntity<String> generateToken()
    {

        return new ResponseEntity<>(jwtUtil.generateToken("keerthana"), HttpStatus.OK);

    }
}
