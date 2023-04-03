package com.senatic.servervotingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senatic.servervotingsystem.model.dto.UsuarioDTO;
import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.model.mapper.UsuarioMapper;
import com.senatic.servervotingsystem.service.UsuariosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class LoginController {

    private final UsuarioMapper usuarioMapper;
    private final UsuariosService usuariosService;

    @GetMapping("user")
    public ResponseEntity<UsuarioDTO> handleGetUsuario(Authentication authentication){
        Usuario usuario = usuariosService.findByUsername(authentication.getName()).get();
        UsuarioDTO usuarioDTO = usuarioMapper.pojoToDto(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTO);
    }

}
