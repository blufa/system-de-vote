package com.senatic.servervotingsystem.service.Implementation;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.servervotingsystem.model.entity.Imagen;
import com.senatic.servervotingsystem.repository.ImagenesRepository;
import com.senatic.servervotingsystem.service.ImagenesService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class ImagenesServiceImpl implements ImagenesService {

    private final ImagenesRepository imagenesRepository;

    @Override
    public void addImagen(Imagen imagen) {
        imagenesRepository.save(imagen);
    }

    @Override
    public Optional<Imagen> getImagenById(String idImagen) {
        return imagenesRepository.findById(idImagen);
    }

    @Override
    public void updateBlobById(String idImagen, String image) {
        imagenesRepository.updateImageById(idImagen, image);
    }
    
}
