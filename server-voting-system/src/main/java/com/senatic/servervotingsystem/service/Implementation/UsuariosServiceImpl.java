package com.senatic.servervotingsystem.service.Implementation;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.repository.UsuariosRepository;
import com.senatic.servervotingsystem.service.UsuariosService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuariosRepository usuariosRepository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuariosRepository.findByUsername(username);
    }

    @Override
    public void addUsuario(Usuario usuario) {
        usuariosRepository.save(usuario);
    }

    @Override
    public Boolean alreadyExist(Usuario usuario) {
        return usuariosRepository.exists(Example.of(usuario));
    }

}
