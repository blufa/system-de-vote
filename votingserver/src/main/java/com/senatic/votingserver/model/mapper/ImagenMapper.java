package com.senatic.votingserver.model.mapper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.senatic.votingserver.model.entity.Imagen;
import com.senatic.votingserver.service.FileHandlerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagenMapper implements GenericMapper<Imagen, MultipartFile>{

    private final FileHandlerService fileHandlerService;

    @Override
    public Imagen dtoToPojo(MultipartFile dto) {
        Imagen imagen = Imagen.builder()
                        .image(fileHandlerService.dtoToPojo(dto))
                        .build();
        return imagen;
    }

    @Override
    public MultipartFile pojoToDto(Imagen pojo) {
        MultipartFile file = fileHandlerService.pojoToDto(pojo.getImage());
        return file;
    }
    
}
