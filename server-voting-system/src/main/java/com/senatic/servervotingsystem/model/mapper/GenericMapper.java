package com.senatic.servervotingsystem.model.mapper;

/**
Esta interfaz define un mapeador genérico entre un objeto DTO (Data Transfer Object) y un objeto POJO (Plain Old Java Object).
@param <P> el tipo de objeto POJO.
@param <D> el tipo de objeto DTO.
*/
public interface GenericMapper<P, D> {
    /**
    Convierte un objeto DTO a un objeto POJO.
    @param dto el objeto DTO a convertir.
    @return el objeto POJO correspondiente.
    */
    P dtoToPojo(D dto);
    /**
    Convierte un objeto POJO a un objeto DTO.
    @param pojo el objeto POJO a convertir.
    @return el objeto DTO correspondiente.
    */
    D pojoToDto(P pojo);
}
