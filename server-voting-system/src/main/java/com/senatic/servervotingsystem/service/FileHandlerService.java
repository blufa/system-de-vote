package com.senatic.servervotingsystem.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.senatic.servervotingsystem.model.dto.AprendizDTO;
import com.senatic.servervotingsystem.model.mapper.GenericMapper;

/**
    Esta interfaz proporciona métodos para manejar archivos y convertirlos en objetos de transferencia de datos.
*/
public interface FileHandlerService extends GenericMapper<String, MultipartFile> {

    /**
    Convierte un archivo CSV en una lista de objetos de transferencia de datos de aprendiz.
    @param csv el archivo CSV a convertir.
    @return una lista de objetos de transferencia de datos de aprendiz que se extrajeron del archivo CSV.
    */
    List<AprendizDTO> readCsvToAprendizDTO(MultipartFile csv);

    /**
    Verifica si el formato del archivo es válido.
    @param filename el nombre del archivo a verificar.
    @param endsWith la extensión de archivo que se espera que tenga el archivo.
    @return true si el formato del archivo es válido, false de lo contrario.
    */
    boolean isFormatValid(String filename, String endsWith);

    /**
    Convierte un objeto MultipartFile en un objeto de tipo File.
    @param file el objeto MultipartFile a convertir.
    @return un objeto de tipo File que representa el archivo asociado con el objeto MultipartFile.
    @throws IOException si se produce un error al leer o escribir el archivo.
    */
    File convertMultiPartToFile(MultipartFile file) throws IOException;
    
}
