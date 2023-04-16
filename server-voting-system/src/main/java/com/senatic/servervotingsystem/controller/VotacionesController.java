package com.senatic.servervotingsystem.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senatic.servervotingsystem.service.EstadisticasService;
import com.senatic.servervotingsystem.service.VotacionesService;
import com.senatic.servervotingsystem.controller.exceptionHandler.exception.EntityAlreadyOnStateException;
import com.senatic.servervotingsystem.controller.exceptionHandler.exception.EntityNotFoundException;
import com.senatic.servervotingsystem.model.dto.VotacionDTO;
import com.senatic.servervotingsystem.model.entity.Estadisticas;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.mapper.VotacionMapper;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/votaciones")
public class VotacionesController {

    private final VotacionesService votacionesService;
    private final EstadisticasService estadisticasService;
    private final VotacionMapper votacionMapper;

    /**
    Obtiene una lista paginada de todas las votaciones.
    @param page El número de página (predeterminado 0).
    @param size El tamaño de página (predeterminado 6).
    @return La respuesta HTTP con la lista paginada de votaciones.
    */
    @GetMapping
    public ResponseEntity<Page<VotacionDTO>> handleGetVotaciones(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Votacion> votaciones = votacionesService.getVotacionesPaginate(paging);
        if (votaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<VotacionDTO> votacionesDTO = new PageImpl<VotacionDTO>(
                votaciones.map(votacionMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(votacionesDTO);
    }

    /**
    Obtiene una lista paginada de votaciones que coinciden con un ejemplo de votación proporcionado.
    @param exampleDTO El ejemplo de votación a buscar.
    @param page El número de página (predeterminado 0).
    @param size El tamaño de página (predeterminado 6).
    @return La respuesta HTTP con la lista paginada de votaciones que coinciden con el ejemplo proporcionado.
    @throws NullPointerException Si exampleDTO es nulo.
    */
    @GetMapping("/search")
    public ResponseEntity<Page<VotacionDTO>> handleSearchVotacion(
            @RequestBody VotacionDTO exampleDTO,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size) {

        Example<Votacion> example = Example.of(votacionMapper.dtoToPojo(exampleDTO));
        Pageable paging = PageRequest.of(page, size);
        Page<Votacion> votaciones = votacionesService.getVotacionesPaginateByExample(paging, example);
        if (votaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<VotacionDTO> votacionesDTO = new PageImpl<VotacionDTO>(
                votaciones.map(votacionMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(votacionesDTO);
    }

    /**
    Actualiza la votación actual estableciendo una nueva votación como actual.
    @param id El identificador de la votación a establecer como actual.
    @return La respuesta HTTP con la votación actualizada.
    @throws EntityNotFoundException Si no se encuentra la votación con el identificador proporcionado.
    @throws IllegalStateException Si la votación no está habilitada para ser establecida como actual.
    */
    @PutMapping("current/{id}")
    public ResponseEntity<VotacionDTO> handleSetCurrentVotacion(@PathVariable Integer id)
            throws EntityNotFoundException {
        if (!votacionesService.alreadyExist(id)) {
            throw new EntityNotFoundException("VOTACION not found. Can not be setted current");
        }
        if (votacionesService.isDisabled(id)) {
            throw new IllegalStateException("VOTACION is not enabled. Can not be current");
        }
        votacionesService.setCurrentVotacion(id);
        VotacionDTO votacionDTO = votacionMapper.pojoToDto(votacionesService.getCurrentVotacion().get());
        return ResponseEntity.status(HttpStatus.OK).body(votacionDTO);
    }

    /**
    Actualiza una votación existente con la información proporcionada en el objeto VotacionDTO.
    @param votacionDTO El objeto VotacionDTO con la información de la votación a actualizar.
    @return La respuesta HTTP con el estado de la operación.
    @throws EntityNotFoundException Si no se encuentra una votación existente con la información proporcionada.
    @throws NullPointerException Si votacionDTO es nulo.
    */
    @PutMapping
    public ResponseEntity<HttpStatus> handleUpdateVotacion(@RequestBody VotacionDTO votacionDTO)
            throws EntityNotFoundException {
        if (!votacionesService.alreadyExist(votacionDTO)) {
            throw new EntityNotFoundException("VOTACION not found. Can not be updated");
        }
        Votacion votacion = votacionMapper.dtoToPojo(votacionDTO);
        votacionesService.addVotacion(votacion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Borra una votación con el id proporcionado.
    @param idVotacion El id de la votación a borrar.
    @return La respuesta HTTP con el código de estado 200 (OK) si la votación se borra correctamente.
    @throws EntityNotFoundException Si no se encuentra la votación con el id proporcionado.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> handleDeleteVotacion(@PathVariable("id") Integer idVotacion)
            throws EntityNotFoundException {
        if (!votacionesService.alreadyExist(idVotacion)) {
            throw new EntityNotFoundException("VOTACION not found. Can not be deleted");
        }
        votacionesService.deleteById(idVotacion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Crea una nueva votación.
    @param votacionDTO Los datos de la votación a crear.
    @return La respuesta HTTP con el estado 201 CREATED si la votación fue creada exitosamente.
    @throws EntityAlreadyOnStateException Si ya existe una votación con el mismo identificador.
    */
    @PostMapping
    public ResponseEntity<HttpStatus> handleSaveVotacion(@RequestBody VotacionDTO votacionDTO)
            throws EntityAlreadyOnStateException {
        if (votacionesService.alreadyExist(votacionDTO)) {
            throw new EntityAlreadyOnStateException("VOTACION already exist. Can not be added");
        }
        Votacion votacion = votacionMapper.dtoToPojo(votacionDTO);
        votacionesService.addVotacion(votacion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
    Deshabilita una votación dado su identificador de votación.
    @param idVotacion El identificador de la votación a deshabilitar.
    @return La respuesta HTTP con el código de estado 200 (OK) si la votación se deshabilita correctamente.
    @throws EntityNotFoundException Si no se encuentra la votación con el identificador proporcionado.
    @throws EntityAlreadyOnStateException Si la votación ya está deshabilitada.
    */
    @PatchMapping("/disable/{id}")
    public ResponseEntity<HttpStatus> handleDisableVotacion(@PathVariable("id") Integer idVotacion)
            throws EntityNotFoundException, EntityAlreadyOnStateException {
        if (!votacionesService.alreadyExist(idVotacion)) {
            throw new EntityNotFoundException("VOTACION not found. Can not be disabled");
        } else if (votacionesService.isDisabled(idVotacion)) {
            throw new EntityAlreadyOnStateException("VOTACION already disabled. Do not request disable");
        }
        votacionesService.disableVotacionById(idVotacion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Habilita una votación existente a través de su ID.
    @param idVotacion El ID de la votación a habilitar.
    @return La respuesta HTTP con el estado OK si la votación ha sido habilitada con éxito.
    @throws EntityNotFoundException si la votación no existe.
    @throws EntityAlreadyOnStateException si la votación ya está habilitada.
    */
    @PatchMapping("/enable/{id}")
    public ResponseEntity<HttpStatus> handleEnableVotacion(@PathVariable("id") Integer idVotacion)
            throws EntityNotFoundException, EntityAlreadyOnStateException {
        if (!votacionesService.alreadyExist(idVotacion)) {
            throw new EntityNotFoundException("VOTACION not found. Can not be enabled");
        } else if (votacionesService.isEnabled(idVotacion)) {
            throw new EntityAlreadyOnStateException("VOTACION already enabled. Do not request enable");
        }
        votacionesService.enableVotacionById(idVotacion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Obtiene las estadísticas de una votación específica.
    @param idVotacion El identificador de la votación.
    @return La respuesta HTTP con las estadísticas de la votación proporcionada.
    @throws EntityNotFoundException si no se encuentra la votación.
    @throws IllegalStateException si la votación no está desactivada.
    */
    @GetMapping("/estadisticas/{id}")
    public ResponseEntity<Estadisticas> handleGetEstadisticas(@PathVariable("id") Integer idVotacion)
            throws EntityNotFoundException, IllegalStateException {
        if (!votacionesService.alreadyExist(idVotacion)) {
            throw new EntityNotFoundException("VOTACION not found. ESTADISTICAS can not be provided");
        } else if (votacionesService.isEnabled(idVotacion)) {
            throw new IllegalStateException("VOTACION is not disabled. ESTADISTICAS can not be provided");
        }
        Votacion votacion = votacionesService.getVotacionById(idVotacion).get();
        Estadisticas estadisticas = estadisticasService.getEstadisticas(votacion);
        return ResponseEntity.status(HttpStatus.OK).body(estadisticas);
    }

    @InitBinder
    public void stringBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
