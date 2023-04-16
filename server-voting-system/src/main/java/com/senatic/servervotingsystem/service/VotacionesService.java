package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senatic.servervotingsystem.model.dto.VotacionDTO;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.enums.EstadoVotacion;

/**
    Esta interfaz proporciona servicios para manejar votaciones.
 */
public interface VotacionesService {

    /**
    Devuelve una lista de todas las votaciones existentes.
    @return una lista de votaciones.
    */
    List<Votacion> getVotaciones();

    /**
    Agrega una nueva votación.
    @param votacion la votación a agregar.
    */
    void addVotacion(Votacion votacion);

    /**
    Elimina una votación por su id.
    @param idVotacion el id de la votación a eliminar.
    */
    void deleteById(Integer idVotacion);

    /**
    Elimina una votación.
    @param votacion la votación a eliminar.
    */
    void deleteVotacion(Votacion votacion);

    /**
    Devuelve una página de votaciones con paginación.
    @param paging información de paginación.
    @return una página de votaciones.
    */
    Page<Votacion> getVotacionesPaginate(Pageable paging);

    /**
    Devuelve una página de votaciones que coinciden con los criterios de búsqueda dados.
    @param paging información de paginación.
    @param example un ejemplo de la votación a buscar.
    @return una página de votaciones.
    */
    Page<Votacion> getVotacionesPaginateByExample(Pageable paging, Example<Votacion> example);

    /**
    Devuelve una votación por su id.
    @param idVotacion el id de la votación a buscar.
    @return un Optional que contiene la votación si se encuentra, o vacío si no.
    */
    Optional<Votacion> getVotacionById(Integer idVotacion);

    /**
    Deshabilita una votación por su id.
    @param idVotacion el id de la votación a deshabilitar.
    */
    void disableVotacionById(Integer idVotacion);

    /**
    Habilita una votación por su id.
    @param idVotacion el id de la votación a habilitar.
    */
    void enableVotacionById(Integer idVotacion);

    /**
    Devuelve una lista de votaciones que tienen el estado dado.
    @param estado el estado de las votaciones a buscar.
    @return una lista de votaciones.
    */
    List<Votacion> getVotacionesByEstado(EstadoVotacion estado);

    /**
    Comprueba si una votación ya existe en la base de datos.
    @param votacionDTO la votación a comprobar.
    @return true si la votación ya existe, false en caso contrario.
    */
    Boolean alreadyExist(VotacionDTO votacionDTO);

    /**
    Comprueba si una votación ya existe en la base de datos.
    @param idVotacion el id de la votación a comprobar.
    @return true si la votación ya existe, false en caso contrario.
    */
    Boolean alreadyExist(Integer idVotacion);

    /**
    Verifica si la votación con el identificador dado está habilitada.
    @param idVotacion el identificador de la votación a verificar.
    @return verdadero si la votación con el identificador dado está habilitada, falso en caso contrario.
    */
    Boolean isEnabled(Integer idVotacion);

    /**
    Verifica si la votación con el identificador dado está deshabilitada.
    @param idVotacion el identificador de la votación a verificar.
    @return verdadero si la votación con el identificador dado está deshabilitada, falso en caso contrario.
    */
    Boolean isDisabled(Integer idVotacion);

    /**
    Obtiene la votación actual seleccionada.
    @return un objeto Optional que contiene la votación actual seleccionada, o un objeto Optional vacío si no hay ninguna votación actual seleccionada.
    */
    Optional<Votacion> getCurrentVotacion();


    /**
    Selecciona la votación actual con el identificador dado.
    @param idVotacion el identificador de la votación a seleccionar como la votación actual.
    */
    void setCurrentVotacion(Integer idVotacion);

    /**
    Quita la selección de la votación actual.
    @param idVotacion el identificador de la votación a deseleccionar como la votación actual.
    */
    void setNotCurrentVotacion(Integer idVotacion);

    /**
    Verifica si hay alguna votación actual seleccionada.
    @return verdadero si hay alguna votación actual seleccionada, falso en caso contrario.
    */
    Boolean isAnyCurrentSelected();

    /**
    Verifica si la votación con el identificador dado es la votación actual seleccionada.
    @param idVotacion el identificador de la votación a verificar.
    @return verdadero si la votación con el identificador dado es la votación actual seleccionada, falso en caso contrario.
    */
    Boolean isThisCurrentVotacion(Integer idVotacion);

}
