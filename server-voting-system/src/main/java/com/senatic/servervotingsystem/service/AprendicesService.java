package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.enums.EstadoAprendiz;

/**
	Esta interfaz define los métodos necesarios para el manejo de aprendices.
*/
public interface AprendicesService {

	/**
    Obtiene una página de aprendices.
    @param paging información sobre la página a obtener.
    @return la página de aprendices correspondiente.
    */
    Page<Aprendiz> getAprendicesPaginate(Pageable paging);

	/**
    Agrega un nuevo aprendiz.
    @param aprendiz el aprendiz a agregar.
    */
    void addAprendiz(Aprendiz aprendiz);

	/**
    Busca un aprendiz por su ID.
    @param id el ID del aprendiz a buscar.
    @return el aprendiz correspondiente al ID, si existe.
    */
	Optional<Aprendiz> findById(String id);

	/**
    Actualiza la información de un aprendiz.
    @param aprendizDto el DTO que contiene la información actualizada del aprendiz.
    */
	void updateAprendiz(AprendizDTO aprendizDto);

	/**
    Busca un aprendiz por su ID.
    @param idAprendiz el ID del aprendiz a buscar.
    @return el aprendiz correspondiente al ID, si existe.
    */
	Optional<Aprendiz> getAprendizById(String idAprendiz);

	/**
    Agrega una lista de aprendices.
    @param aprendices la lista de aprendices a agregar.
    */
	void addAprendices(List<Aprendiz> aprendices);

	/**
    Obtiene una página de aprendices que coincidan con un ejemplo.
    @param paging información sobre la página a obtener.
    @param example el ejemplo que se usará para buscar los aprendices.
    @return la página de aprendices correspondiente al ejemplo.
    */
	Page<Aprendiz> getAprendicesPaginateByExample(Pageable paging, Example<Aprendiz> example);

	/**
    Cuenta el número de aprendices con un estado dado.
    @param estado el estado para el que se contará el número de aprendices.
    @return el número de aprendices con el estado dado.
    */
	Integer countAprendicesByEstado(EstadoAprendiz estado);

	/**
    Verifica si ya existe un aprendiz con la misma información que el DTO dado.
    @param aprendizDTO el DTO que contiene la información del aprendiz a verificar.
    @return true si ya existe un aprendiz con la misma información, false de lo contrario.
    */
	Boolean alreadyExist(AprendizDTO aprendizDTO);

	/**
    Verifica si ya existe un aprendiz con un ID dado.
    @param idAprendiz el ID del aprendiz a verificar.
    @return true si ya existe un aprendiz con el mismo ID, false de lo contrario.
    */
	Boolean alreadyExist(String idAprendiz);

	/**
    Elimina un aprendiz por su ID.
    @param id el ID del aprendiz a eliminar.
    */
    void deleteById(String id);
    
}
