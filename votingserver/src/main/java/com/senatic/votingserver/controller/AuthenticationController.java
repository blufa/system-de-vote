package com.senatic.votingserver.controller;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senatic.votingserver.configuration.security.model.AuthenticationRequest;
import com.senatic.votingserver.configuration.security.model.AuthenticationResponse;
import com.senatic.votingserver.configuration.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) throws InvalidCredentialsException {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

    @GetMapping("app-working")
    public ResponseEntity<String> handleAppIsWorkingMessage(){
        return ResponseEntity.status(HttpStatus.SC_OK).body("App is working well :)");
    }

}
