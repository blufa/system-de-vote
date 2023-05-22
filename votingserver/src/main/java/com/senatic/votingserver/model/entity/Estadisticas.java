package com.senatic.votingserver.model.entity;

import java.util.List;
import java.util.Map;

import com.senatic.votingserver.model.dto.CandidatoDTO;
import com.senatic.votingserver.model.dto.VotacionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estadisticas {
    
    private VotacionDTO votacion;

    private Long cantidadVotos;

    private Long votantesHabilitados;

    private List<CandidatoDTO> candidatos;

    private Map<String, Long> votosPorCandidato;

    private CandidatoDTO candidatoMasVotado;
    
    private String creationDateTime;

}
