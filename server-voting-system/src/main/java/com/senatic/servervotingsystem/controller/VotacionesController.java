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
import com.senatic.servervotingsystem.model.dto.VotacionDTO;
import com.senatic.servervotingsystem.model.entity.Estadisticas;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.mapper.VotacionMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/votaciones")
@RequiredArgsConstructor
@Validated
public class VotacionesController {

    private final VotacionesService votacionesService;
    private final EstadisticasService estadisticasService;
    private final VotacionMapper votacionMapper;

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

    @PutMapping
    public ResponseEntity<HttpStatus> handleUpdateVotacion(@RequestBody VotacionDTO votacionDTO) {
        if (!votacionesService.alreadyExist(votacionDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Votacion votacion = votacionMapper.dtoToPojo(votacionDTO);
        votacionesService.addVotacion(votacion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> handleCreateVotacion(@PathVariable("id") Integer idVotacion) {
        if (!votacionesService.alreadyExist(idVotacion)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        votacionesService.deleteById(idVotacion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> handleSaveVotacion(@RequestBody VotacionDTO votacionDTO) {
        if (votacionesService.alreadyExist(votacionDTO)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Votacion votacion = votacionMapper.dtoToPojo(votacionDTO);
        votacionesService.addVotacion(votacion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<HttpStatus> handleDisableVotacion(@PathVariable("id") Integer idVotacion) {
        if (!votacionesService.alreadyExist(idVotacion)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (votacionesService.isEnabled(idVotacion)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        votacionesService.disableVotacionById(idVotacion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/enable/{id}")
    public ResponseEntity<HttpStatus> handleEnableVotacion(@PathVariable("id") Integer idVotacion) {
        if (!votacionesService.alreadyExist(idVotacion)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (votacionesService.isEnabled(idVotacion)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        votacionesService.enableVotacionById(idVotacion);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/estadisticas/{id}")
    public ResponseEntity<Estadisticas> handleGetEstadisticas(@PathVariable("id") Integer idVotacion) {
        if (!votacionesService.alreadyExist(idVotacion)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (!votacionesService.isDisabled(idVotacion)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
