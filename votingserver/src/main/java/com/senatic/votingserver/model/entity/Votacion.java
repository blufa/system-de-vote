package com.senatic.votingserver.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.senatic.votingserver.model.entity.enums.EstadoVotacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="votaciones")
public class Votacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nombre;

    @Column(length = 250)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoVotacion estado;

    private Boolean currentVotacion;
    
    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime lastModified;
    
}
