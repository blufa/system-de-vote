package com.senatic.votingserver.model.mapper;


import org.springframework.stereotype.Service;

import com.senatic.votingserver.model.dto.CandidatoDTO;
import com.senatic.votingserver.model.entity.Aprendiz;
import com.senatic.votingserver.model.entity.Candidato;
import com.senatic.votingserver.model.entity.Imagen;
import com.senatic.votingserver.model.entity.Votacion;
import com.senatic.votingserver.model.entity.enums.EstadoCandidato;
import com.senatic.votingserver.service.AprendicesService;
import com.senatic.votingserver.service.VotacionesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidatoMapper implements GenericMapper<Candidato, CandidatoDTO> {

    private final AprendicesService aprendicesService;
    private final VotacionesService votacionesService;

    @Override
    public Candidato dtoToPojo(CandidatoDTO dto) {
        Aprendiz aprendiz = aprendicesService.findById(dto.getDocumento()).get();
        Votacion votacion = votacionesService.getVotacionById(dto.getIdVotacion()).get();
        Imagen imagen = Imagen.builder().id(dto.getDocumento()).image(dto.getImagen()).build();
        Candidato candidato = Candidato.builder()
                .aprendiz(aprendiz)
                .imagen(imagen)
                .votacion(votacion)
                .propuestas(dto.getPropuestas())
                .estado(EstadoCandidato.HABILITADO)
                .build();
        if (dto.getId() != null) {
            candidato.setId(dto.getId());
        }
        return candidato;
    }

    @Override
    public CandidatoDTO pojoToDto(Candidato pojo) {
        CandidatoDTO candidatoDTO = CandidatoDTO.builder()
                .id(pojo.getId())
                .documento(pojo.getAprendiz().getId())
                .imagen(pojo.getImagen().getImage())
                .idVotacion(pojo.getVotacion().getId())
                .propuestas(pojo.getPropuestas())
                .build();
        return candidatoDTO;
    }

}
