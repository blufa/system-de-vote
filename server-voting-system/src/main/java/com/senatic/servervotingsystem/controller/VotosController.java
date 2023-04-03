package com.senatic.servervotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senatic.servervotingsystem.service.VotosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/votos")
@RequiredArgsConstructor
public class VotosController {

    private final VotosService votosService;

    @GetMapping("/vote-by/{idCandidato}")
    public ResponseEntity<HttpStatus> saveVoto(@PathVariable("idCandidato") Integer idCandidato, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String idAprendiz = authentication.getName();
            votosService.registerVote(idCandidato, idAprendiz);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
