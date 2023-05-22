package com.senatic.votingserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senatic.votingserver.model.dto.AprendizDTO;
import com.senatic.votingserver.model.entity.Aprendiz;
import com.senatic.votingserver.model.entity.enums.EstadoAprendiz;
import com.senatic.votingserver.model.mapper.AprendizMapper;
import com.senatic.votingserver.repository.AprendicesRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class AprendicesService {

    private final AprendicesRepository aprendicesRepository;
    private final UsuariosService usuariosService;
    private final AprendizMapper aprendizMapper;

    public Page<Aprendiz> getAprendicesPaginate(Pageable paging) {
        return aprendicesRepository.findAll(paging);
    }    

    public void addAprendiz(Aprendiz aprendiz) {
        if (!usuariosService.alreadyExist(aprendiz.getUsuario())) {
            usuariosService.addUsuario(aprendiz.getUsuario());
            aprendicesRepository.save(aprendiz);
        }
    }

    public Optional<Aprendiz> findById(String id) {
        return aprendicesRepository.findById(id);
    }

    public void updateAprendiz(AprendizDTO aprendizDto) {
        Aprendiz aprendiz = aprendizMapper.dtoToPojo(aprendizDto);
        aprendicesRepository.save(aprendiz);
    }

    public Optional<Aprendiz> getAprendizById(String idAprendiz) {
        return aprendicesRepository.findById(idAprendiz);
    }

    public void addAprendices(List<Aprendiz> aprendices) {
        aprendices.forEach(aprendiz -> addAprendiz(aprendiz));
    }

    public Page<Aprendiz> getAprendicesPaginateByExample(Pageable paging, Example<Aprendiz> example) {
        return aprendicesRepository.findAll(example, paging);
    }

    public Integer countAprendicesByEstado(EstadoAprendiz estado) {
        return aprendicesRepository.countByEstado(estado);
    }

    public Boolean alreadyExist(AprendizDTO aprendizDTO) {
        return aprendicesRepository.existsById(aprendizDTO.getNumeroDocumento());
    }

    public Boolean alreadyExist(String idAprendiz) {
        return aprendicesRepository.existsById(idAprendiz);
    }

    public void deleteById(String id) {
        aprendicesRepository.deleteById(id);
    }
    
}
