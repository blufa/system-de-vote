package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Map;

import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Estadisticas;
import com.senatic.servervotingsystem.model.entity.Votacion;

public interface EstadisticasService {
    Estadisticas getEstadisticas(Votacion votacion);
    long getVotantesHabilitados();
    long getCantidadVotos(Votacion votacion);
    List<Candidato> getCandidatos(Integer idVotacion);
    Map<String, Long> getVotosPorCandidato(Integer idVotacion);
    Candidato getCandidatoMasVotado(Integer idVotacion);
}
