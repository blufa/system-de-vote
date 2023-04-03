package com.senatic.servervotingsystem.model.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senatic.servervotingsystem.model.dto.UsuarioDTO;
import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.model.entity.enums.AuthorityEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioMapper implements GenericMapper<Usuario, UsuarioDTO> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario dtoToPojo(UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .authority(AuthorityEnum.valueOf(dto.getAuthority()))
                .enabled(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .build();
        if (dto.getId() != null) {
            usuario.setId(dto.getId());
        }
        return usuario;
    }

    @Override
    public UsuarioDTO pojoToDto(Usuario pojo) {
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(pojo.getId())
                .username(pojo.getUsername())
                .password(pojo.getPassword())
                .authority(pojo.getAuthority().toString())
                .build();
        return usuarioDTO;
    }

}
