package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senatic.servervotingsystem.model.dto.CandidatoDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;

/**
    Esta interfaz define los servicios que pueden ser ofrecidos para el manejo de Candidato.
*/
public interface CandidatosService {
    
    /**
    Obtiene una lista paginada de todos los candidatos.
    @param page el objeto pageable que contiene la información de la paginación.
    @return una página con los candidatos solicitados.
    */
    Page<Candidato> getCandidatosPaginate(Pageable page);

    /**
    Obtiene una lista paginada de todos los candidatos que cumplen con un ejemplo dado.
    @param page el objeto pageable que contiene la información de la paginación.
    @param example el objeto example que define las características de los candidatos buscados.
    @return una página con los candidatos solicitados.
    */
    Page<Candidato> getCandidatosPaginateByExample(Pageable page, Example<Candidato> example);

    /**
    Elimina un candidato dado su identificador.
    @param idCandidato el identificador del candidato a eliminar.
    */
    void deleteCandidatoById(Integer idCandidato);

    /**
    Obtiene un candidato dado su identificador.
    @param idCandidato el identificador del candidato a buscar.
    @return un objeto Optional con el candidato encontrado o vacío si no existe.
    */
    Optional<Candidato> getCandidatoById(Integer idCandidato);

    /**
    Elimina un candidato dado un objeto Candidato.
    @param candidato el objeto Candidato a eliminar.
    */
    void deleteCandidato(Candidato candidato);

    /**
    Agrega un nuevo candidato a la base de datos.
    @param candidato el objeto Candidato a agregar.
    */
    void addCandidato(Candidato candidato);

    /**
    Desactiva un candidato dado su identificador.
    @param idCandidato el identificador del candidato a desactivar.
    */
    void disableCandidatoById(Integer idCandidato);

    /**
    Activa un candidato dado su identificador.
    @param idCandidato el identificador del candidato a activar.
    */
    void enableCandidatoById(Integer idCandidato);

    /**
    Obtiene una lista de todos los candidatos que pertenecen a una votación y que cumplen con un estado dado.
    @param idVotacion el identificador de la votación a la que pertenecen los candidatos buscados.
    @param estado el estado que deben cumplir los candidatos buscados.
    @return una lista con los candidatos solicitados.
    */
    List<Candidato> getCandidatosByVotacionAndEstado(Integer idVotacion, EstadoCandidato estado);

    /**
    Obtiene un candidato dado un objeto Aprendiz.
    @param aprendiz el objeto Aprendiz a buscar en la base de datos.
    @return un objeto Optional con el candidato encontrado o vacío si no existe.
    */
    Optional<Candidato> getCandidatoByAprendiz(Aprendiz aprendiz);

    /**
    Verifica si un candidato ya existe en una votación.
    @param candidato el objeto Candidato a verificar.
    @return true si el candidato ya existe en la votación, false en caso contrario.
    */
    Boolean alreadyExistOnVotacion(Candidato candidato);

    /**
    Verifica si un candidato ya existe en la base de datos.
    @param candidatoDTO objeto con los datos del candidato a verificar.
    @return true si el candidato ya existe en la base de datos, false de lo contrario.
    */
    Boolean alreadyExist(CandidatoDTO candidatoDTO);

    /**
    Verifica si un candidato con un ID específico ya existe en la base de datos.
    @param idCandidato el ID del candidato a verificar.
    @return true si un candidato con el ID especificado ya existe en la base de datos, false de lo contrario.
    */
    Boolean alreadyExist(Integer idCandidato);

    /**
    Verifica si un candidato con un ID específico ya está habilitado en la base de datos.
    @param idCandidato el ID del candidato a verificar.
    @return true si el candidato con el ID especificado está habilitado en la base de datos, false de lo contrario.
    */
    Boolean alreadyEnabled(Integer idCandidato);

    /**
    Verifica si un candidato con un ID específico ya está deshabilitado en la base de datos.
    @param idCandidato el ID del candidato a verificar.
    @return true si el candidato con el ID especificado está deshabilitado en la base de datos, false de lo contrario.
    */
    Boolean alreadyDisabled(Integer idCandidato);
    
}
