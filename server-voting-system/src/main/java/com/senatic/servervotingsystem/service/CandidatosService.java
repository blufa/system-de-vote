package com.senatic.servervotingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senatic.servervotingsystem.model.dto.CandidatoDTO;
import com.senatic.servervotingsystem.model.entity.Aprendiz;
import com.senatic.servervotingsystem.model.entity.Candidato;
import com.senatic.servervotingsystem.model.entity.enums.EstadoCandidato;

public interface CandidatosService {
    Page<Candidato> getCandidatosPaginate(Pageable page);
    Page<Candidato> getCandidatosPaginateByExample(Pageable page, Example<Candidato> example);
    void deleteCandidatoById(Integer idCandidato);
    Optional<Candidato> getCandidatoById(Integer idCandidato);
    void deleteCandidato(Candidato candidato);
    void addCandidato(Candidato candidato);
    void disableCandidatoById(Integer idCandidato);
    void enableCandidatoById(Integer idCandidato);
    List<Candidato> getCandidatosByVotacionAndEstado(Integer idVotacion, EstadoCandidato estado);
    Optional<Candidato> getCandidatoByAprendiz(Aprendiz aprendiz);
    Boolean alreadyExistOnVotacion(Candidato candidato);
    Boolean alreadyExist(CandidatoDTO candidatoDTO);
    Boolean alreadyExist(Integer idCandidato);
    Boolean alreadyEnabled(Integer idCandidato);
    Boolean alreadyDisabled(Integer idCandidato);
}
