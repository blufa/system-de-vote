package com.senatic.servervotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senatic.servervotingsystem.service.VotosService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/votos")
public class VotosController {

    private final VotosService votosService;

    /**
    Registra un voto para un candidato específico, realizado por un aprendiz autenticado.
    @param idCandidato El identificador del candidato por el cual se desea votar.
    @param authentication La información de autenticación del aprendiz que realiza el voto.
    @return La respuesta HTTP con el código de estado OK si el voto fue registrado correctamente.
    */
    @PostMapping("/vote-by/{idCandidato}")
    public ResponseEntity<HttpStatus> saveVoto(@PathVariable("idCandidato") Integer idCandidato,
            Authentication authentication) {
        String idAprendiz = authentication.getName();
        votosService.registerVote(idCandidato, idAprendiz);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Registra un voto para un candidato específico, realizado por un aprendiz autenticado.
    @param idCandidato El identificador del candidato por el cual se desea votar.
    @param authentication La información de autenticación del aprendiz que realiza el voto.
    @return La respuesta HTTP con el código de estado OK si el voto fue registrado correctamente.
    */
    @GetMapping("/has-already-vote/{idAprendiz}")
    public ResponseEntity<Boolean> handleHasAlreadyVote(@PathVariable("idAprendiz") String idAprendiz) {
        return ResponseEntity.status(HttpStatus.OK).body(votosService.hasAlreadyVote(idAprendiz));
    }

}
