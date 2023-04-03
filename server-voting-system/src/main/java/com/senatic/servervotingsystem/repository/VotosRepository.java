package com.senatic.servervotingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.Votacion;
import com.senatic.servervotingsystem.model.entity.Voto;

@Repository
public interface VotosRepository extends JpaRepository<Voto, Integer> {

    Optional<Voto> findByAprendizAndVotacion(Aprendiz aprendiz, Votacion votacion);
    List<Voto> findByVotacion(Votacion votacion);
    List<Voto> findByVotacionAndCandidato(Votacion votacion, Candidato candidato);
    @Query(value="SELECT * FROM votos v WHERE v.idAprendiz= :idAprendiz AND v.idVotacion= :idVotacion LIMIT 1"  , nativeQuery = true)
    Optional<Voto> findByAprendizAndVotacion(String idAprendiz, Integer idVotacion);

}
