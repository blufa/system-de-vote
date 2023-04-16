package com.senatic.servervotingsystem.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.senatic.servervotingsystem.controller.exceptionHandler.exception.EntityAlreadyExistException;
import com.senatic.servervotingsystem.controller.exceptionHandler.exception.EntityNotFoundException;
import com.senatic.servervotingsystem.controller.exceptionHandler.exception.FileNotValidException;
import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.mapper.AprendizMapper;
import com.senatic.servervotingsystem.service.AprendicesService;
import com.senatic.servervotingsystem.service.FileHandlerService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/aprendices")
public class AprendicesController {

    private final AprendicesService aprendicesService;
    private final AprendizMapper aprendizMapper;
    private final FileHandlerService fileHandlerService;

    /**
    Obtiene una lista paginada de todos los aprendices.
    @param page El número de página (predeterminado 0).
    @param size El tamaño de página (predeterminado 9).
    @return La respuesta HTTP con la lista paginada de aprendices.
    */
    @GetMapping
    public ResponseEntity<Page<AprendizDTO>> handleGetAprendices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Aprendiz> aprendices = aprendicesService.getAprendicesPaginate(paging);
        if (aprendices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<AprendizDTO> aprendicesDTO = new PageImpl<AprendizDTO>(aprendices.map(aprendizMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(aprendicesDTO);
    }

    /**
    Realiza una búsqueda de aprendices basada en un ejemplo proporcionado, devolviendo una lista paginada de resultados.
    @param exampleDTO El ejemplo de AprendizDTO a buscar.
    @param page El número de página a recuperar (predeterminado 0).
    @param size El tamaño de página a recuperar (predeterminado 9).
    @return La respuesta HTTP con la lista paginada de aprendices encontrados.
    */
    @GetMapping("/search")
    public ResponseEntity<Page<AprendizDTO>> handleSearchByExample(
            @RequestBody AprendizDTO exampleDTO,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "9") Integer size) {
        Example<Aprendiz> example = Example.of(aprendizMapper.dtoToPojo(exampleDTO));
        Pageable paging = PageRequest.of(page, size);
        Page<Aprendiz> aprendices = aprendicesService.getAprendicesPaginateByExample(paging, example);
        if (aprendices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        Page<AprendizDTO> aprendicesDTO = new PageImpl<AprendizDTO>(aprendices.map(aprendizMapper::pojoToDto).toList());
        return ResponseEntity.status(HttpStatus.OK).body(aprendicesDTO);
    }

    /**
    Crea un nuevo aprendiz.
    @param aprendizDTO La información del aprendiz a crear.
    @return La respuesta HTTP con el estado de la creación del aprendiz.
    @throws EntityAlreadyExistException Si el aprendiz ya existe en la base de datos.
    */
    @PostMapping
    public ResponseEntity<HttpStatus> handleCreateAprendiz(@RequestBody AprendizDTO aprendizDTO)
            throws EntityAlreadyExistException {
        if (aprendicesService.alreadyExist(aprendizDTO)) {
            throw new EntityAlreadyExistException("APRENDIZ already exist");
        }
        Aprendiz aprendiz = aprendizMapper.dtoToPojo(aprendizDTO);
        aprendicesService.addAprendiz(aprendiz);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
    Guarda un grupo de aprendices a partir de un archivo CSV.
    @param csvFile El archivo CSV que contiene los aprendices a guardar.
    @return La respuesta HTTP indicando si la operación fue exitosa o no.
    @throws FileNotValidException Si el archivo no es válido.
    */
    @PostMapping("/csv")
    public ResponseEntity<HttpStatus> handleSaveAprendicesByCSV(@RequestBody MultipartFile csvFile)
            throws FileNotValidException {
        if (!csvFile.isEmpty()) {
            if (fileHandlerService.isFormatValid(csvFile.getOriginalFilename(), ".csv")) {
                List<AprendizDTO> aprendicesDTO = fileHandlerService.readCsvToAprendizDTO(csvFile);
                List<Aprendiz> aprendices = aprendicesDTO.stream().map(aprendizMapper::dtoToPojo).toList();
                aprendicesService.addAprendices(aprendices);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            throw new FileNotValidException("File must be in valid format");
        }
        throw new FileNotValidException("File must not be empty. Can not be readed");
    }

    /**
    Elimina un aprendiz por su ID.
    @param id El ID del aprendiz a eliminar.
    @return La respuesta HTTP indicando si se realizó o no la eliminación.
    @throws EntityNotFoundException Si el aprendiz con el ID proporcionado no existe.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> handleDeleteAprendizById(@PathVariable String id)
            throws EntityNotFoundException {
        if (!aprendicesService.alreadyExist(id)) {
            throw new EntityNotFoundException("APRENDIZ not found with id: " + id);
        }
        aprendicesService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
    Actualiza un aprendiz existente.
    @param aprendizDTO El aprendiz con la información actualizada.
    @return La respuesta HTTP con el estado de la actualización.
    @throws EntityNotFoundException si el aprendiz no existe.
    */
    @PutMapping
    public ResponseEntity<HttpStatus> handleEditAprendiz(@RequestBody AprendizDTO aprendizDTO)
            throws EntityNotFoundException {
        if (!aprendicesService.alreadyExist(aprendizDTO)) {
            throw new EntityNotFoundException("APRENDIZ not found with id: " + aprendizDTO.getNumeroDocumento());
        }
        aprendicesService.updateAprendiz(aprendizDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
