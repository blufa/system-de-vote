package com.senatic.votingserver.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.votingserver.model.entity.Aprendiz;
import com.senatic.votingserver.model.entity.Candidato;
import com.senatic.votingserver.model.entity.Estadisticas;
import com.senatic.votingserver.model.entity.Votacion;
import com.senatic.votingserver.model.entity.enums.EstadoAprendiz;
import com.senatic.votingserver.model.entity.enums.EstadoCandidato;
import com.senatic.votingserver.model.mapper.CandidatoMapper;
import com.senatic.votingserver.model.mapper.VotacionMapper;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class EstadisticasService {

    private final AprendicesService aprendicesService;
    private final CandidatosService candidatosService;
    private final VotosService votosService;
    private final VotacionesService votacionesService;
    private final VotacionMapper votacionMapper;
    private final CandidatoMapper candidatoMapper;

    public Estadisticas getEstadisticas(Votacion votacion) {
        Estadisticas estadisticas = new Estadisticas();

        estadisticas.setVotacion(votacionMapper.pojoToDto(votacion));
        estadisticas.setCandidatoMasVotado(candidatoMapper.pojoToDto(getCandidatoMasVotado(votacion.getId())));
        estadisticas.setCandidatos(getCandidatos(votacion.getId()).stream().map(candidatoMapper::pojoToDto).toList());
        estadisticas.setCantidadVotos(getCantidadVotos(votacion));
        estadisticas.setVotantesHabilitados(getVotantesHabilitados());
        estadisticas.setVotosPorCandidato(getVotosPorCandidato(votacion.getId()));
        estadisticas.setCreationDateTime(LocalDateTime.now().toString());

        return estadisticas;
    }

    public long getVotantesHabilitados() {
        return aprendicesService.countAprendicesByEstado(EstadoAprendiz.EN_FORMACION);
    }

    public long getCantidadVotos(Votacion votacion) {
        return votosService.countByVotacion(votacion);
    }

    public List<Candidato> getCandidatos(Integer idVotacion) {
        return candidatosService.getCandidatosByVotacionAndEstado(idVotacion, EstadoCandidato.HABILITADO);
    }

    public Map<String, Long> getVotosPorCandidato(Integer idVotacion) {
        Map<String, Long> votosPorCandidato = new HashMap<String, Long>();
        getCandidatos(idVotacion).stream().forEach(candidato -> {
            Votacion votacion = votacionesService.getVotacionById(idVotacion).get();
            long cantidadVotos = votosService.getByVotacionAndCandidato(votacion, candidato).stream().count();
            votosPorCandidato.put(candidato.getAprendiz().getId(), cantidadVotos);
        });
        return votosPorCandidato;
    }

    public Candidato getCandidatoMasVotado(Integer idVotacion) {
        String idAprendiz = Collections.max(getVotosPorCandidato(idVotacion).entrySet(), Map.Entry.comparingByValue()).getKey();
        Aprendiz aprendiz = aprendicesService.getAprendizById(idAprendiz).get();
        Optional<Candidato> optional = candidatosService.getCandidatoByAprendiz(aprendiz);
        return optional.isPresent() ? optional.get() : Candidato.builder().build();
    }
    
}
