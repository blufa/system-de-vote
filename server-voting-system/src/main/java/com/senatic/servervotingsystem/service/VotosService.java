package com.senatic.servervotingsystem.service;

import java.util.List;

import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.Voto;

/**
    Esta interfaz proporciona métodos para manejar votos.
*/
public interface VotosService {

    /**
    Verifica si un aprendiz ya ha votado en una votación dada.
    @param idAprendiz el ID del aprendiz que se desea verificar.
    @param idVotacion el ID de la votación en la que se desea verificar si el aprendiz ha votado.
    @return true si el aprendiz ya ha votado en la votación, false de lo contrario.
    */
    Boolean hasAlreadyVote(String idAprendiz, Integer idVotacion);

    /**
    Verifica si un aprendiz ya ha votado en una votación dada.
    @param aprendiz el aprendiz que se desea verificar.
    @param votacion la votación en la que se desea verificar si el aprendiz ha votado.
    @return true si el aprendiz ya ha votado en la votación, false de lo contrario.
    */
    Boolean hasAlreadyVote(Aprendiz aprendiz, Votacion votacion);

    /**
    Obtiene todos los votos registrados para una votación dada.
    @param votacion la votación para la que se desean obtener los votos.
    @return una lista de todos los votos registrados para la votación.
    */
    List<Voto> getByVotacion(Votacion votacion);

    /**
    Obtiene el número de votos registrados para una votación dada.
    @param votacion la votación para la que se desea obtener el número de votos.
    @return el número de votos registrados para la votación.
    */
    long countByVotacion(Votacion votacion);

    /**
    Obtiene todos los votos registrados para una votación y un candidato dados.
    @param votacion la votación para la que se desean obtener los votos.
    @param candidato el candidato para el que se desean obtener los votos.
    @return una lista de todos los votos registrados para la votación y el candidato dados.
    */
    List<Voto> getByVotacionAndCandidato(Votacion votacion, Candidato candidato);

    /**
    Registra un voto en la base de datos.
    @param voto el voto a registrar.
    */
    void registerVote(Voto voto);

    /**
    Registra un voto en la base de datos a partir del ID de candidato y el ID de aprendiz dados.
    @param idCandidato el ID del candidato por el que se está votando.
    @param idAprendiz el ID del aprendiz que está votando.
    */
    void registerVote(Integer idCandidato, String idAprendiz);

    /**
    Verifica si un usuario con una autoridad determinada ya ha votado.
    @param authority la autoridad del usuario que se desea verificar.
    @return true si el usuario con la autoridad dada ya ha votado, false de lo contrario.
    */
    Boolean hasAlreadyVote(String authority);
    
}
