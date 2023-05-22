package com.senatic.votingserver.model.dto;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;
    @NotEmpty
    private String username;
    @ReadOnlyProperty
    private String password;
    @NotEmpty
    @JsonAlias("role")
	private String authority;
    
}
