package com.senatic.votingserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.votingserver.model.dto.CandidatoDTO;
import com.senatic.votingserver.model.entity.Aprendiz;
import com.senatic.votingserver.model.entity.Candidato;
import com.senatic.votingserver.model.entity.enums.EstadoCandidato;
import com.senatic.votingserver.repository.CandidatosRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class CandidatosService {

    private final CandidatosRepository candidatosRepository;
    private final ImagenesService imagenesService;
    private final VotacionesService votacionesService;
    private final AprendicesService aprendicesService;

    public Page<Candidato> getCandidatosPaginate(Pageable page) {
        return candidatosRepository.findAll(page);
    }

    public Page<Candidato> getCandidatosPaginateByExample(Pageable page, Example<Candidato> example) {
        return candidatosRepository.findAll(example, page);
    }

    public void deleteCandidatoById(Integer idCandidato) {
        candidatosRepository.deleteById(idCandidato);
    }

    public Optional<Candidato> getCandidatoById(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato);
    }

    public void deleteCandidato(Candidato candidato) {
        candidatosRepository.delete(candidato);
    }

    public void addCandidato(Candidato candidato) {
        if (!candidatosRepository.exists(Example.of(candidato))) {
            imagenesService.addImagen(candidato.getImagen());
        } else {
            imagenesService.updateBlobById(candidato.getAprendiz().getId(), candidato.getImagen().getImage());
        }
        candidatosRepository.save(candidato);
    }

    public void disableCandidatoById(Integer idCandidato) {
        candidatosRepository.disableCandidatoById(idCandidato);
    }

    public void enableCandidatoById(Integer idCandidato) {
        candidatosRepository.enableCandidatoById(idCandidato);
    }

    public List<Candidato> getCandidatosByVotacionAndEstado(Integer idVotacion, EstadoCandidato estado) {
        return candidatosRepository.findByVotacionAndEstado(votacionesService.getVotacionById(idVotacion).get(),
                estado);
    }

    public Optional<Candidato> getCandidatoByAprendiz(Aprendiz aprendiz) {
        return candidatosRepository.findByAprendiz(aprendiz);
    }

    public Boolean alreadyExistOnVotacion(Candidato candidato) {
        return candidatosRepository.findByVotacionAndEstado(candidato.getVotacion(), EstadoCandidato.HABILITADO)
                .stream()
                .filter(c -> c.getAprendiz().getId().equals(candidato.getAprendiz().getId())).findFirst().isPresent();
    }

    public Boolean alreadyExist(CandidatoDTO candidatoDTO) {
        return candidatosRepository.findByAprendiz(aprendicesService.findById(candidatoDTO.getDocumento()).get())
                .isPresent();
    }

    public Boolean alreadyExist(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato) != null;
    }

    public Boolean alreadyEnabled(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato).get().getEstado() == EstadoCandidato.HABILITADO;
    }

    public Boolean alreadyDisabled(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato).get().getEstado() == EstadoCandidato.INHABILITADO;
    }

}
