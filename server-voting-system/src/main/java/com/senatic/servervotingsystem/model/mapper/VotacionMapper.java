package com.senatic.servervotingsystem.model.mapper;

import org.springframework.stereotype.Service;

import com.senatic.servervotingsystem.model.dto.VotacionDTO;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.enums.EstadoVotacion;


@Service
public class VotacionMapper implements GenericMapper<Votacion, VotacionDTO> {
    
    @Override
    public Votacion dtoToPojo(VotacionDTO dto) {
        Votacion votacion = Votacion.builder()
                                    .nombre(dto.getNombre())
                                    .descripcion(dto.getDescripcion())
                                    .estado(EstadoVotacion.valueOf(dto.getEstado()))
                                    .currentVotacion(false)
                                    .build();
        if (dto.getId() != null) {
            votacion.setId(dto.getId());
        }
        return votacion;
    }

    @Override
    public VotacionDTO pojoToDto(Votacion pojo) {
        VotacionDTO votacionDTO = VotacionDTO.builder()
                                            .id(pojo.getId())
                                            .nombre(pojo.getNombre())
                                            .descripcion(pojo.getDescripcion())
                                            .estado(pojo.getEstado().toString())
                                            .current(pojo.getCurrentVotacion())
                                            .build();
        return votacionDTO;
    }
    
}
