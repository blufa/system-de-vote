package com.senatic.servervotingsystem.configuration.security;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.senatic.servervotingsystem.model.entity.Usuario;
import com.senatic.servervotingsystem.service.UsuariosService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class AppUserDetails implements UserDetailsService {

    private final UsuariosService usuariosService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> customerOptional = usuariosService.findByUsername(username);

        if (!customerOptional.isPresent()) {
            throw new UsernameNotFoundException("User details not found: " + username);
        }

        Usuario usuario = customerOptional.get();

        String password = usuario.getPassword();
        boolean disabled = !(usuario.getEnabled());
        boolean accountExpired = !(usuario.getAccountNonExpired());
        boolean accountLocked = !(usuario.getAccountNonLocked());
        boolean credentialsExpired = !(usuario.getCredentialsNonExpired());
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(usuario.getAuthority().toString());
        
        return User.builder()
                .username(username)
                .password(password)
                .disabled(disabled)
                .accountExpired(accountExpired)
                .accountLocked(accountLocked)
                .credentialsExpired(credentialsExpired)
                .authorities(List.of(simpleGrantedAuthority))
                .build();
    }
    
}
