package com.senatic.servervotingsystem.service;

import java.util.Optional;

import com.senatic.servervotingsystem.model.entity.Imagen;

public interface ImagenesService {
    void addImagen(Imagen imagen);   
    Optional<Imagen> getImagenById(String idImagen);
    void updateBlobById(String idImagen, String image);
}
