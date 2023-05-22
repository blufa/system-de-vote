package com.senatic.votingserver.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.votingserver.model.entity.Usuario;
import com.senatic.votingserver.repository.UsuariosRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;
    // private final AuthenticationService authenticationService;


    public Optional<Usuario> findByUsername(String username) {
        return usuariosRepository.findByUsername(username);
    }

    public void addUsuario(Usuario usuario) {
        usuariosRepository.save(usuario);
        //authenticationService.registerNewUserToken(usuarioSaved);
    }

    public Boolean alreadyExist(Usuario usuario) {
        return usuariosRepository.exists(Example.of(usuario));
    }

}
