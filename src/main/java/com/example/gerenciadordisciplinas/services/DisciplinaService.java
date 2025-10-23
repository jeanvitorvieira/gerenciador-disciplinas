package com.example.gerenciadordisciplinas.services;

import com.example.gerenciadordisciplinas.dtos.DisciplinaRequestDTO;
import com.example.gerenciadordisciplinas.dtos.DisciplinaResponseDTO;
import com.example.gerenciadordisciplinas.exceptions.ConflictException;
import com.example.gerenciadordisciplinas.exceptions.NotFoundException;
import com.example.gerenciadordisciplinas.mappers.DisciplinaMapper;
import com.example.gerenciadordisciplinas.models.Disciplina;
import com.example.gerenciadordisciplinas.repositories.DisciplinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Service
public class DisciplinaService {

    private static final Logger log = LoggerFactory.getLogger(DisciplinaService.class);

    private final DisciplinaRepository disciplinaRepository;
    private final DisciplinaMapper disciplinaMapper;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, DisciplinaMapper disciplinaMapper) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
        log.info("DisciplinaService inicializado com sucesso.");
    }

    public DisciplinaResponseDTO createDisciplina(DisciplinaRequestDTO disciplinaDto) {
        log.info("Tentativa de criação de nova disciplina: {}", disciplinaDto.nomeDisciplina());

        if (disciplinaRepository.existsByNomeDisciplina(disciplinaDto.nomeDisciplina())) {
            log.warn("Conflito detectado. Disciplina com nome '{}' já existe.", disciplinaDto.nomeDisciplina());
            throw new ConflictException("Já existe uma disciplina com o nome: " + disciplinaDto.nomeDisciplina());
        }

        log.debug("Mapeando DTO para entidade e preparando para salvar.");
        Disciplina novaDisciplina = new Disciplina();
        novaDisciplina.setNomeDisciplina(disciplinaDto.nomeDisciplina());
        novaDisciplina.setProfessorDisciplina(disciplinaDto.professorDisciplina());
        novaDisciplina.setUniversidade("UniSATC");

        Disciplina disciplinaSalva = disciplinaRepository.save(novaDisciplina);
        log.info("Disciplina '{}' (ID: {}) salva com sucesso.", disciplinaSalva.getNomeDisciplina(), disciplinaSalva.getId());

        return disciplinaMapper.toDto(disciplinaSalva);
    }

    public List<DisciplinaResponseDTO> getAllDisciplinas(int paginaAtual, int tamanhoPagina) {
        log.info("Buscando todas as disciplinas (Página: {}, Tamanho: {})", paginaAtual, tamanhoPagina);

        Pageable pageable = PageRequest.of(paginaAtual, tamanhoPagina, Sort.by("nomeDisciplina"));
        Page<Disciplina> page = disciplinaRepository.findAll(pageable);

        log.debug("Retornando {} de {} disciplinas encontradas.", page.getNumberOfElements(), page.getTotalElements());
        return page.stream().map(disciplinaMapper::toDto).toList();
    }

    public DisciplinaResponseDTO getDisciplina(UUID id) {
        log.info("Buscando disciplina pelo ID: {}", id);

        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Disciplina não encontrada para o ID: {}", id);
                    return new NotFoundException("Disciplina não encontrada");
                });

        log.debug("Disciplina '{}' encontrada com sucesso.", disciplina.getNomeDisciplina());
        return disciplinaMapper.toDto(disciplina);
    }

    public void deleteDisciplina(UUID id) {
        log.info("Tentativa de exclusão de disciplina pelo ID: {}", id);

        if (!disciplinaRepository.existsById(id)) {
            log.warn("Tentativa de exclusão falhou. Disciplina não encontrada para o ID: {}", id);
            throw new NotFoundException("Disciplina não encontrada");
        }

        disciplinaRepository.deleteById(id);
        log.info("Disciplina com ID {} excluída com sucesso.", id);
    }

    public DisciplinaResponseDTO patchDisciplina(UUID id, DisciplinaRequestDTO novosDados) {
        log.info("Tentativa de atualização (PATCH) da disciplina ID: {}", id);

        Disciplina disciplinaExistente = disciplinaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Atualização falhou. Disciplina não encontrada para o ID: {}", id);
                    return new NotFoundException("Disciplina não encontrada");
                });

        if (novosDados.nomeDisciplina() != null) {
            log.debug("Atualizando nome para: {}", novosDados.nomeDisciplina());

            if (disciplinaRepository.existsByNomeDisciplina(novosDados.nomeDisciplina())) {
                log.warn("Conflito detectado durante o PATCH. Novo nome '{}' já está em uso.", novosDados.nomeDisciplina());
                throw new ConflictException("Já existe uma disciplina com o nome: " + novosDados.nomeDisciplina());
            }

            disciplinaExistente.setNomeDisciplina(novosDados.nomeDisciplina());
        }

        if (novosDados.professorDisciplina() != null) {
            log.debug("Atualizando professor para: {}", novosDados.professorDisciplina());
            disciplinaExistente.setProfessorDisciplina(novosDados.professorDisciplina());
        }

        Disciplina disciplinaAtualizada = disciplinaRepository.save(disciplinaExistente);
        log.info("Disciplina ID {} atualizada com sucesso.", id);

        return disciplinaMapper.toDto(disciplinaAtualizada);
    }
}