package com.senatic.servervotingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Votacion;

/**
    Repositorio para la entidad Votacion.
*/
@Repository
public interface VotacionesRepository extends JpaRepository<Votacion, Integer> {
    
    /**
    Deshabilita una votación mediante su id.
    @param idVotacion el id de la votación a deshabilitar.
    */
    @Modifying
    @Query(value="UPDATE votaciones c SET c.estado = 'INHABILITADA' WHERE c.id= :idVotacion" , nativeQuery = true)
    void disableVotacionById(Integer idVotacion);
    
    /**
    Habilita una votación mediante su id.
    @param idVotacion el id de la votación a habilitar.
    */
    @Modifying
    @Query(value="UPDATE votaciones c SET c.estado = 'HABILITADA' WHERE c.id= :idVotacion" , nativeQuery = true)
    void enableVotacionById(Integer idVotacion);

    /**
    Establece una votación como la votación actual mediante su id.
    @param idVotacion el id de la votación a establecer como la votación actual.
    */
    @Modifying
    @Query(value="UPDATE votaciones c SET c.currentVotacion = True WHERE c.id= :idVotacion" , nativeQuery = true)
    void setCurrentById(Integer idVotacion);

    /**
    Establece una votación como no siendo la votación actual mediante su id.
    @param idVotacion el id de la votación a establecer como no siendo la votación actual.
    */
    @Modifying
    @Query(value="UPDATE votaciones c SET c.currentVotacion = False WHERE c.id= :idVotacion" , nativeQuery = true)
    void setNotCurrentById(Integer idVotacion);

    /**
    Busca una votación mediante su nombre y descripción.
    @param nombre el nombre de la votación a buscar.
    @param descripcion la descripción de la votación a buscar.
    @return un Optional con la votación correspondiente si existe.
    */
    Optional<Votacion> findByNombreAndDescripcion(String nombre, String descripcion);

    /**
    Busca la votación actual.
    @param currentVotacion true si se busca la votación actual, false de lo contrario.
    @return un Optional con la votación actual si existe.
    */
    Optional<Votacion> findByCurrentVotacion(boolean currentVotacion);

}
