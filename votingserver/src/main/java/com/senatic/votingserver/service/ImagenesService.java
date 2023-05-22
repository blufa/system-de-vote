package com.senatic.votingserver.service;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.votingserver.model.entity.Imagen;
import com.senatic.votingserver.repository.ImagenesRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class ImagenesService {

    private final ImagenesRepository imagenesRepository;

    public void addImagen(Imagen imagen) {
        imagenesRepository.save(imagen);
    }

    public Optional<Imagen> getImagenById(String idImagen) {
        return imagenesRepository.findById(idImagen);
    }

    public void updateBlobById(String idImagen, String image) {
        imagenesRepository.updateImageById(idImagen, image);
    }
    
}
