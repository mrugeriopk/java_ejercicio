package com.login.controller;

import com.login.model.input.Credenciales;
import com.login.service.Iservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private Iservice service;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credenciales cre){
        return service.login(cre);
    }

}
