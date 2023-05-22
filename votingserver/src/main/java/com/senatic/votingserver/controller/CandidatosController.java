package com.senatic.votingserver.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
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

import com.senatic.votingserver.controller.exceptionHandler.exception.EntityAlreadyOnStateException;
import com.senatic.votingserver.controller.exceptionHandler.exception.EntityNotFoundException;
import com.senatic.votingserver.model.dto.CandidatoDTO;
import com.senatic.votingserver.model.entity.Candidato;
import com.senatic.votingserver.model.entity.Votacion;
import com.senatic.votingserver.model.entity.enums.EstadoCandidato;
import com.senatic.votingserver.model.mapper.CandidatoMapper;
import com.senatic.votingserver.service.CandidatosService;
import com.senatic.votingserver.service.VotacionesService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/candidatos")
public class CandidatosController {

    private final CandidatosService candidatosService;
    private final VotacionesService votacionesService;
    private final CandidatoMapper candidatoMapper;
    public static final Logger logger = LogManager.getLogger(CandidatosController.class);


    /**
    Maneja solicitudes GET para obtener una página de candidatos en formato DTO.
    @param page El número de página a obtener. Por defecto, es 0.
    @param size El número de elementos por página. Por defecto, es 9.
    @return Un objeto ResponseEntity que contiene una página de candidatos en formato DTO y un estado HTTP que indica si la operación fue exitosa o no.
    */
    @GetMapping
    public ResponseEntity<Page<CandidatoDTO>> handleGetCandidatos(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Candidato> candidatos = candidatosService.getCandidatosPaginate(paging);
        if (candidatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<CandidatoDTO> candidatosDTO = new PageImpl<CandidatoDTO>(
                candidatos.map(candidatoMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(candidatosDTO);
    }

    /**
    Obtiene una lista de los candidatos habilitados en la votación actual, si existe.
    @return Un objeto ResponseEntity con un cuerpo que contiene la lista de candidatos habilitados en la votación actual y un código de estado HTTP que indica el resultado de la operación.
    Si no existe una votación actual, el código de estado será NOT_FOUND.
    Si no hay candidatos habilitados en la votación actual, el código de estado será NOT_FOUND.
    */
    @GetMapping("/current-votacion")
    //@Timed(value = "handleGetCandidatoByCurrentVotacion.time", description = "Time taken to return Candidatos of current Votacion")
    public ResponseEntity<List<CandidatoDTO>> handleGetCandidatoByCurrentVotacion() {

        Optional<Votacion> currentOptional = votacionesService.getCurrentVotacion();
        if (currentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Candidato> candidatos = candidatosService.getCandidatosByVotacionAndEstado(currentOptional.get().getId(),
                EstadoCandidato.HABILITADO);
        if (candidatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<CandidatoDTO> candidatosDTO = candidatos.stream().map(candidatoMapper::pojoToDto).toList();

        return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(candidatosDTO);
    }

    /**
    Busca candidatos que coincidan con los criterios especificados en el objeto CandidatoDTO proporcionado.
    @param candidatoDTO El objeto CandidatoDTO con los criterios de búsqueda.
    @param page El número de página que se desea obtener (por defecto, 0).
    @param size El tamaño de la página que se desea obtener (por defecto, 9).
    @return Un objeto ResponseEntity con un cuerpo que contiene una página de candidatos que coinciden con los criterios de búsqueda y un código de estado HTTP que indica el resultado de la operación.
    @throws EntityNotFoundException Si no se encontró ningún candidato que coincida con los criterios de búsqueda.
    */
    @GetMapping("/search")
    public ResponseEntity<Page<CandidatoDTO>> handleSearchCandidato(
            @RequestBody CandidatoDTO candidatoDTO,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {
        Example<Candidato> example = Example.of(candidatoMapper.dtoToPojo(candidatoDTO));
        Pageable paging = PageRequest.of(page, size);
        Page<Candidato> candidatos = candidatosService.getCandidatosPaginateByExample(paging, example);
        if (candidatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<CandidatoDTO> candidatosDTO = new PageImpl<CandidatoDTO>(
                candidatos.map(candidatoMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(candidatosDTO);
    }

    /**
    Crea un nuevo candidato a partir de un objeto CandidatoDTO.
    @param candidatoDTO El objeto CandidatoDTO que contiene la información del candidato a crear.
    @return Un objeto ResponseEntity con un código de estado HTTP que indica el resultado de la operación.
    @throws EntityAlreadyOnStateException Si ya existe un candidato con el mismo ID en la votación actual.
    */
    @PostMapping
    public ResponseEntity<HttpStatus> handleCreateCandidato(@RequestBody CandidatoDTO candidatoDTO)
            throws EntityAlreadyOnStateException {
        Candidato candidato = candidatoMapper.dtoToPojo(candidatoDTO);
        if (candidatosService.alreadyExistOnVotacion(candidato)) {
            throw new EntityAlreadyOnStateException("CANDIDATO already exist on VOTACION. Can not be added");
        }
        candidatosService.addCandidato(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
    Elimina el candidato especificado por su ID.
    @param id El ID del candidato a eliminar.
    @return Un objeto ResponseEntity con un código de estado HTTP que indica el resultado de la operación.
    @throws EntityNotFoundException Si el candidato especificado no existe.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> handleDeleteCandidatoById(
            @PathVariable Integer id) throws EntityNotFoundException {
        if (!candidatosService.alreadyExist(id)) {
            throw new EntityNotFoundException("CANDIDATO not found. Can not be deleted");
        }
        candidatosService.deleteCandidatoById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Actualiza un candidato existente con la información proporcionada.
    @param candidatoDTO La información actualizada del candidato.
    @return Un objeto ResponseEntity con un código de estado HTTP que indica el resultado de la operación.
    @throws EntityNotFoundException Si no se encuentra un candidato con el ID proporcionado en la base de datos.
    */
    @PutMapping
    public ResponseEntity<HttpStatus> handleUpdateCandidato(
            @RequestBody CandidatoDTO candidatoDTO) throws EntityNotFoundException {
        if (!candidatosService.alreadyExist(candidatoDTO)) {
            throw new EntityNotFoundException("CANDIDATO not found. Can not be updated");
        }
        Candidato candidato = candidatoMapper.dtoToPojo(candidatoDTO);
        candidatosService.addCandidato(candidato);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Habilita un candidato especificado por su ID.
    @param idCandidato El ID del candidato que se desea habilitar.
    @return Un objeto ResponseEntity con un código de estado HTTP que indica el resultado de la operación.
    @throws EntityNotFoundException Si el candidato especificado no existe.
    @throws EntityAlreadyOnStateException Si el candidato especificado ya se encuentra habilitado.
    */
    @PatchMapping("/enable/{id}")
    public ResponseEntity<HttpStatus> enableCandidatoById(@PathVariable("id") Integer idCandidato)
            throws EntityNotFoundException, EntityAlreadyOnStateException {
        if (!candidatosService.alreadyExist(idCandidato)) {
            throw new EntityNotFoundException("CANDIDATO not found. Can not be enabled");
        } else if (candidatosService.alreadyEnabled(idCandidato)) {
            throw new EntityAlreadyOnStateException("CANDIDATO already enabled. Do not request enable");
        }
        candidatosService.enableCandidatoById(idCandidato);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Deshabilita un candidato especificado por su ID.
    @param idCandidato El ID del candidato que se desea deshabilitar.
    @return Un objeto ResponseEntity con un código de estado HTTP que indica el resultado de la operación.
    @throws EntityNotFoundException Si el candidato especificado no existe.
    @throws EntityAlreadyOnStateException Si el candidato especificado ya se encuentra deshabilitado.
    */
    @PatchMapping("/disable/{id}")
    public ResponseEntity<HttpStatus> disableCandidatoById(@PathVariable("id") Integer idCandidato)
            throws EntityNotFoundException, EntityAlreadyOnStateException {
        if (!candidatosService.alreadyExist(idCandidato)) {
            throw new EntityNotFoundException("CANDIDATO not found. Can not be disabled");
        } else if (candidatosService.alreadyDisabled(idCandidato)) {
            throw new EntityAlreadyOnStateException("CANDIDATO already disabled. Do not request disable");
        }
        candidatosService.disableCandidatoById(idCandidato);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
