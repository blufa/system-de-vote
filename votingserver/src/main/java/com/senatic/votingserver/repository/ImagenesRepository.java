package com.senatic.votingserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senatic.votingserver.model.entity.Imagen;

/**
    Repositorio para el manejo de imágenes.
*/
@Repository
public interface ImagenesRepository extends JpaRepository<Imagen, String> {
    
    /**
    Actualiza la imagen de una imagen existente por su ID.
    @param idImagen el ID de la imagen a actualizar.
    @param image la nueva imagen a establecer.
    */
    @Modifying
    @Query(value = "UPDATE imagenes i SET image=:image WHERE i.id=:idImagen", nativeQuery = true)
    void updateImageById(@Param("idImagen")String idImagen, String image);

}
