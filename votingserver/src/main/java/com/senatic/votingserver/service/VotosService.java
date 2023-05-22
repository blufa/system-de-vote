package com.senatic.votingserver.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.votingserver.model.entity.Aprendiz;
import com.senatic.votingserver.model.entity.Candidato;
import com.senatic.votingserver.model.entity.Votacion;
import com.senatic.votingserver.model.entity.Voto;
import com.senatic.votingserver.repository.VotosRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class VotosService {

    private final VotosRepository votosRepository;
    private final AprendicesService aprendicesService;
    private final VotacionesService votacionesService;
    private final CandidatosService candidatosService;

    public Boolean hasAlreadyVote(String idAprendiz, Integer idVotacion) {
        Aprendiz aprendiz = aprendicesService.getAprendizById(idAprendiz).get();
        Votacion votacion = votacionesService.getVotacionById(idVotacion).get();
        return votosRepository.findByAprendizAndVotacion(aprendiz, votacion).isPresent();
    }

    public List<Voto> getByVotacion(Votacion votacion) {
        return votosRepository.findByVotacion(votacion);
    }

    public long countByVotacion(Votacion votacion) {
        return getByVotacion(votacion).stream().count();
    }

    public List<Voto> getByVotacionAndCandidato(Votacion votacion, Candidato candidato) {
        return votosRepository.findByVotacionAndCandidato(votacion, candidato);
    }

    public synchronized void registerVote(Voto voto) {
        votosRepository.save(voto);
    }

    public void registerVote(Integer idCandidato, String idAprendiz) {
        Candidato candidato = candidatosService.getCandidatoById(idCandidato).get();
        Voto voto = Voto.builder()
                .candidato(candidato)
                .aprendiz(Aprendiz.builder().id(idAprendiz).build())
                .votacion(candidato.getVotacion())
                .valido(true)
                .build();
        votosRepository.save(voto);
    }

    public Boolean hasAlreadyVote(String authority) {
        Votacion votacion = votacionesService.getCurrentVotacion().get();
        return hasAlreadyVote(authority, votacion.getId());
    }

    public Boolean hasAlreadyVote(Aprendiz aprendiz, Votacion votacion) {
        return votosRepository.findByAprendizAndVotacion(aprendiz, votacion).isPresent();
    }

}
