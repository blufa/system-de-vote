package com.senatic.servervotingsystem.service.Implementation;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.servervotingsystem.configuration.security.service.AuthenticationService;
import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.repository.UsuariosRepository;
import com.senatic.servervotingsystem.service.UsuariosService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuariosRepository usuariosRepository;
    private final AuthenticationService authenticationService;


    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuariosRepository.findByUsername(username);
    }

    @Override
    public void addUsuario(Usuario usuario) {
        Usuario usuarioSaved = usuariosRepository.save(usuario);
        authenticationService.registerNewUserToken(usuarioSaved);
    }

    @Override
    public Boolean alreadyExist(Usuario usuario) {
        return usuariosRepository.exists(Example.of(usuario));
    }

}
