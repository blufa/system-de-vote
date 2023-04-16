package com.senatic.servervotingsystem.configuration.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senatic.servervotingsystem.configuration.security.model.AuthenticationRequest;
import com.senatic.servervotingsystem.configuration.security.model.AuthenticationResponse;
import com.senatic.servervotingsystem.configuration.security.model.Token;
import com.senatic.servervotingsystem.configuration.security.repository.TokenRepository;
import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.model.mapper.UserDetailsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserDetailsMapper userDetailsMapper;
    private final AppUserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    /**
    Registra un nuevo token JWT para el usuario dado.
    @param usuario El usuario para el cual se generará el token.
    */
    public void registerNewUserToken(Usuario usuario) {
        String jwtToken = jwtService.generateToken(userDetailsMapper.pojoToDto(usuario));
        saveUserToken(usuario, jwtToken);
    }

    /**
    Autentica al usuario y genera un nuevo token de acceso.
    @param request La solicitud de autenticación que contiene el nombre de usuario y contraseña.
    @return La respuesta de autenticación que contiene el token de acceso generado.
    @throws UsernameNotFoundException si el usuario no existe.
    */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Usuario usuario = userDetailsService.loadUsuarioByUsername(request.getUsername());
        revokeAllUserTokens(usuario);
        String jwtToken = jwtService.generateToken(userDetailsMapper.pojoToDto(usuario));
        saveUserToken(usuario, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
    Guarda el token generado para el usuario en la base de datos.
    @param usuario El usuario para el que se está generando el token.
    @param jwtToken El token generado para el usuario.
    */
    private void saveUserToken(Usuario usuario, String jwtToken) {
        Token token = Token.builder()
                .user(usuario)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
    Revoca todos los tokens de un usuario.
    @param usuario El usuario cuyos tokens serán revocados.
    */
    private void revokeAllUserTokens(Usuario usuario) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(usuario.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}