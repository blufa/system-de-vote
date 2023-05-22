package com.senatic.votingserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.senatic.votingserver.model.entity.Aprendiz;
import com.senatic.votingserver.model.entity.Candidato;
import com.senatic.votingserver.model.entity.Votacion;
import com.senatic.votingserver.model.entity.Voto;

/**
    Esta interfaz define los métodos para realizar operaciones sobre la entidad Voto en la base de datos.
*/
@Repository
public interface VotosRepository extends JpaRepository<Voto, Integer> {

    /**
    Busca un voto específico por el aprendiz y la votación asociados.
    @param aprendiz el aprendiz asociado al voto.
    @param votacion la votación asociada al voto.
    @return un objeto Optional que contiene el voto, o vacío si no se encuentra.
    */
    Optional<Voto> findByAprendizAndVotacion(Aprendiz aprendiz, Votacion votacion);

    /**
    Busca todos los votos asociados a una votación específica.
    @param votacion la votación asociada a los votos.
    @return una lista de votos asociados a la votación especificada.
    */
    List<Voto> findByVotacion(Votacion votacion);

    /**
    Busca todos los votos asociados a una votación y un candidato específicos.
    @param votacion la votación asociada a los votos.
    @param candidato el candidato asociado a los votos.
    @return una lista de votos asociados a la votación y el candidato especificados.
    */
    List<Voto> findByVotacionAndCandidato(Votacion votacion, Candidato candidato);

    /**
    Busca un voto específico por el ID del aprendiz y el ID de la votación asociados.
    @param idAprendiz el ID del aprendiz asociado al voto.
    @param idVotacion el ID de la votación asociada al voto.
    @return un objeto Optional que contiene el voto, o vacío si no se encuentra.
    */
    @Query(value="SELECT * FROM votos v WHERE v.idAprendiz= :idAprendiz AND v.idVotacion= :idVotacion LIMIT 1"  , nativeQuery = true)
    Optional<Voto> findByAprendizAndVotacion(String idAprendiz, Integer idVotacion);

}
