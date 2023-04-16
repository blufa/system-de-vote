package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Map;

import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Estadisticas;
import com.senatic.servervotingsystem.model.entity.Votacion;

/**
    Esta interfaz proporciona métodos para obtener estadísticas de una votación.
*/
public interface EstadisticasService {
        
    /**
    Obtiene las estadísticas de una votación dada.
    @param votacion la votación de la que se desean obtener las estadísticas.
    @return un objeto de tipo Estadisticas que contiene las estadísticas de la votación.
    */
    Estadisticas getEstadisticas(Votacion votacion);

    /**
    Obtiene la cantidad de votantes habilitados para todas las votaciones.
    @return un valor de tipo long que representa la cantidad de votantes habilitados.
    */
    long getVotantesHabilitados();

    /**
    Obtiene la cantidad de votos emitidos para una votación dada.
    @param votacion la votación de la que se desea obtener la cantidad de votos.
    @return un valor de tipo long que representa la cantidad de votos emitidos para la votación dada.
    */
    long getCantidadVotos(Votacion votacion);

    /**
    Obtiene una lista de todos los candidatos para una votación dada.
    @param idVotacion el ID de la votación para la que se desean obtener los candidatos.
    @return una lista de objetos de tipo Candidato que representan a todos los candidatos para la votación dada.
    */
    List<Candidato> getCandidatos(Integer idVotacion);

    /**
    Obtiene un mapa que muestra la cantidad de votos que recibió cada candidato para una votación dada.
    @param idVotacion el ID de la votación para la que se desean obtener los votos por candidato.
    @return un mapa que muestra la cantidad de votos que recibió cada candidato para la votación dada.
    */
    Map<String, Long> getVotosPorCandidato(Integer idVotacion);

    /**
    Obtiene el candidato más votado para una votación dada.
    @param idVotacion el ID de la votación para la que se desea obtener el candidato más votado.
    @return un objeto de tipo Candidato que representa al candidato más votado para la votación dada.
    */
    Candidato getCandidatoMasVotado(Integer idVotacion);
    
}
