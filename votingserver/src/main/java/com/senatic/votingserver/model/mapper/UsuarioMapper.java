package com.senatic.votingserver.model.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.senatic.votingserver.model.dto.UsuarioDTO;
import com.senatic.votingserver.model.entity.Usuario;
import com.senatic.votingserver.model.entity.enums.AuthorityEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioMapper implements GenericMapper<Usuario, UsuarioDTO> {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
