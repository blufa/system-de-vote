package com.senatic.votingserver.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    // private final AuthenticationService authenticationService;

    // @PostMapping
    // public ResponseEntity<AuthenticationResponse> authenticate(
    //         @RequestBody AuthenticationRequest request) {
    //     return ResponseEntity.ok(authenticationService.authenticate(request));
    // }

}
