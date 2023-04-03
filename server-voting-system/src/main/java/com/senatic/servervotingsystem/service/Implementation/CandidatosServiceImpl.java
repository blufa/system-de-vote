package com.senatic.servervotingsystem.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.servervotingsystem.model.dto.CandidatoDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;
import com.senatic.servervotingsystem.repository.CandidatosRepository;
import com.senatic.servervotingsystem.service.AprendicesService;
import com.senatic.servervotingsystem.service.CandidatosService;
import com.senatic.servervotingsystem.service.ImagenesService;
import com.senatic.servervotingsystem.service.VotacionesService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class CandidatosServiceImpl implements CandidatosService {

    private final CandidatosRepository candidatosRepository;
    private final ImagenesService imagenesService;
    private final VotacionesService votacionesService;
    private final AprendicesService aprendicesService;

    @Override
    public Page<Candidato> getCandidatosPaginate(Pageable page) {
        return candidatosRepository.findAll(page);
    }

    @Override
    public Page<Candidato> getCandidatosPaginateByExample(Pageable page, Example<Candidato> example) {
        return candidatosRepository.findAll(example, page);
    }

    @Override
    public void deleteCandidatoById(Integer idCandidato) {
        candidatosRepository.deleteById(idCandidato);
    }

    @Override
    public Optional<Candidato> getCandidatoById(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato);
    }

    @Override
    public void deleteCandidato(Candidato candidato) {
        candidatosRepository.delete(candidato);
    }

    @Override
    public void addCandidato(Candidato candidato) {
        if (!candidatosRepository.exists(Example.of(candidato))) {
            imagenesService.addImagen(candidato.getImagen());
        } else {
            imagenesService.updateBlobById(candidato.getAprendiz().getId(), candidato.getImagen().getImage());
        }
        candidatosRepository.save(candidato);
    }

    @Override
    public void disableCandidatoById(Integer idCandidato) {
        candidatosRepository.disableCandidatoById(idCandidato);
    }

    @Override
    public void enableCandidatoById(Integer idCandidato) {
        candidatosRepository.enableCandidatoById(idCandidato);
    }

    @Override
    public List<Candidato> getCandidatosByVotacionAndEstado(Integer idVotacion, EstadoCandidato estado) {
        return candidatosRepository.findByVotacionAndEstado(votacionesService.getVotacionById(idVotacion).get(),
                estado);
    }

    @Override
    public Optional<Candidato> getCandidatoByAprendiz(Aprendiz aprendiz) {
        return candidatosRepository.findByAprendiz(aprendiz);
    }

    @Override
    public Boolean alreadyExistOnVotacion(Candidato candidato) {
        return candidatosRepository.findByVotacionAndEstado(candidato.getVotacion(), EstadoCandidato.HABILITADO)
                .stream()
                .filter(c -> c.getAprendiz().getId().equals(candidato.getAprendiz().getId())).findFirst().isPresent();
    }

    @Override
    public Boolean alreadyExist(CandidatoDTO candidatoDTO) {
        return candidatosRepository.findByAprendiz(aprendicesService.findById(candidatoDTO.getDocumento()).get())
                .isPresent();
    }

    @Override
    public Boolean alreadyExist(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato) != null;
    }

    @Override
    public Boolean alreadyEnabled(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato).get().getEstado() == EstadoCandidato.HABILITADO;
    }

    @Override
    public Boolean alreadyDisabled(Integer idCandidato) {
        return candidatosRepository.findById(idCandidato).get().getEstado() == EstadoCandidato.INHABILITADO;
    }

}
