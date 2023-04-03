package com.senatic.servervotingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"ficha", "programa", "tipoDocumento", "numeroDocumento", "nombre", "apellido", "celular", "correoElectronico", "estado"})
public class AprendizDTO {
    
    @NotBlank
    private String ficha;
    @NotBlank
    private String programa;
    @NotBlank
    private String tipoDocumento;
    @NotBlank
    private String numeroDocumento;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String celular;
    @NotBlank @Email
    private String correoElectronico;
    @NotBlank
    private String estado;
    
}
