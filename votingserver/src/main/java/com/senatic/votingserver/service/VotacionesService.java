package com.senatic.votingserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.votingserver.model.dto.VotacionDTO;
import com.senatic.votingserver.model.entity.Votacion;
import com.senatic.votingserver.model.entity.enums.EstadoVotacion;
import com.senatic.votingserver.repository.VotacionesRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class VotacionesService {

    private final VotacionesRepository votacionesRepository;

    public List<Votacion> getVotaciones() {
        return votacionesRepository.findAll();
    }

    public void addVotacion(Votacion votacion) {
        votacionesRepository.save(votacion);
    }

    public void deleteById(Integer idVotacion) {
        votacionesRepository.deleteById(idVotacion);
    }

    public void deleteVotacion(Votacion votacion) {
        votacionesRepository.delete(votacion);
    }

    public Page<Votacion> getVotacionesPaginate(Pageable paging) {
        return votacionesRepository.findAll(paging);
    }

    public Page<Votacion> getVotacionesPaginateByExample(Pageable paging, Example<Votacion> example) {
        return votacionesRepository.findAll(example, paging);
    }

    public Optional<Votacion> getVotacionById(Integer idVotacion) {
        return votacionesRepository.findById(idVotacion);
    }

    public void disableVotacionById(Integer idVotacion) {
        if(isThisCurrentVotacion(idVotacion)){
            setNotCurrentVotacion(idVotacion);
        }
        votacionesRepository.disableVotacionById(idVotacion);
    }

    public void enableVotacionById(Integer idVotacion) {
        votacionesRepository.enableVotacionById(idVotacion);
    }

    public List<Votacion> getVotacionesByEstado(EstadoVotacion estado) {
        return votacionesRepository.findAll()
                .stream()
                .filter(votacion -> votacion.getEstado().equals(estado))
                .toList();
    }

    public Boolean alreadyExist(VotacionDTO votacionDTO) {
        return votacionesRepository.findByNombreAndDescripcion(votacionDTO.getNombre(), votacionDTO.getDescripcion())
                .isPresent();
    }

    public Boolean alreadyExist(Integer idVotacion) {
        return votacionesRepository.findById(idVotacion).isPresent();
    }

    public Boolean isDisabled(Integer idVotacion) {
        return votacionesRepository.findById(idVotacion).get().getEstado().equals(EstadoVotacion.INHABILITADA);
    }

    public Boolean isEnabled(Integer idVotacion) {
        return votacionesRepository.findById(idVotacion).get().getEstado().equals(EstadoVotacion.HABILITADA);
    }

    public Optional<Votacion> getCurrentVotacion() {
        return votacionesRepository.findByCurrentVotacion(true);
    }

    public void setCurrentVotacion(Integer idVotacion) {
        if (isAnyCurrentSelected()) {
            Optional<Votacion> currentOptional = votacionesRepository.findByCurrentVotacion(true);
            if (currentOptional.isPresent()) {
                votacionesRepository.setNotCurrentById(currentOptional.get().getId());
            }
        }
        votacionesRepository.setCurrentById(idVotacion);
    }

    public Boolean isAnyCurrentSelected() {
        return votacionesRepository.findByCurrentVotacion(true).isPresent();
    }

    public Boolean isThisCurrentVotacion(Integer idVotacion) {
        Optional<Votacion> optional = votacionesRepository.findByCurrentVotacion(true);
        if (optional.isPresent()) {
            return optional.get().getId() == idVotacion;
        }
        return false;
    }

    public void setNotCurrentVotacion(Integer idVotacion) {
        if (isThisCurrentVotacion(idVotacion)) {
            votacionesRepository.setNotCurrentById(idVotacion);
        }
    }

}
