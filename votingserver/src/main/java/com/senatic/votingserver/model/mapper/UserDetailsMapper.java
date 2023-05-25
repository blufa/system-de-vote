package com.senatic.votingserver.model.mapper;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.senatic.votingserver.model.entity.Usuario;
import com.senatic.votingserver.model.entity.enums.AuthorityEnum;


@Service
public class UserDetailsMapper implements GenericMapper<Usuario, UserDetails> {

    @Override
    public Usuario dtoToPojo(UserDetails dto) {

        AuthorityEnum authority = dto.getAuthorities().stream().findFirst().get().toString().contains("ADMINISTRADOR")
                ? AuthorityEnum.ROLE_ADMINISTRADOR
                : AuthorityEnum.ROLE_APRENDIZ;

        return Usuario.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .enabled(dto.isEnabled())
                .accountNonLocked(dto.isAccountNonLocked())
                .credentialsNonExpired(dto.isCredentialsNonExpired())
                .accountNonExpired(dto.isAccountNonExpired())
                .authority(authority)
                .build();
    }

    @Override
    public UserDetails pojoToDto(Usuario pojo) {
        return User.builder()
                .username(pojo.getUsername())
                .password(pojo.getPassword())
                .disabled(!(pojo.getEnabled()))
                .accountExpired(!(pojo.getAccountNonExpired()))
                .accountLocked(!(pojo.getAccountNonLocked()))
                .credentialsExpired(!(pojo.getCredentialsNonExpired()))
                .authorities(pojo.getAuthority().toString())
                .build();
    }

}
