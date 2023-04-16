package com.senatic.servervotingsystem.service;

import java.util.Optional;

import com.senatic.servervotingsystem.model.entity.Imagen;

/**
    Esta interfaz proporciona métodos para manejar imágenes.
*/
public interface ImagenesService {

    /**
    Agrega una imagen a la base de datos.
    @param imagen la imagen a agregar.
    */
    void addImagen(Imagen imagen);   

    /**
    Obtiene una imagen por su ID.
    @param idImagen el ID de la imagen que se desea obtener.
    @return un objeto Optional que contiene la imagen correspondiente al ID, si existe.
    */
    Optional<Imagen> getImagenById(String idImagen);

    /**
    Actualiza el campo de image(BLOB) de una imagen existente en la base de datos por su ID.
    @param idImagen el ID de la imagen a actualizar.
    @param image el nuevo valor para el campo de image(BLOB) de la imagen.
    */
    void updateBlobById(String idImagen, String image);
    
}
