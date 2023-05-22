package com.senatic.votingserver.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/candidatos")
public class UsuariosController {

    public static final Logger logger = LogManager.getLogger(UsuariosController.class);

    @GetMapping("app-working")
    public ResponseEntity<String> handleAppIsWorkingMessage(){
        return ResponseEntity.status(HttpStatus.OK).body("App is working well :)");
    }
    
}
