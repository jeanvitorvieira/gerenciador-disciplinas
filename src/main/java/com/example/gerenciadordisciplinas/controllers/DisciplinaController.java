package com.example.gerenciadordisciplinas.controllers;

import com.example.gerenciadordisciplinas.dtos.DisciplinaRequestDTO;
import com.example.gerenciadordisciplinas.dtos.DisciplinaResponseDTO;
import com.example.gerenciadordisciplinas.services.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @Operation(summary = "Cadastra uma nova disciplina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
    })
    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> createDisciplina(@Valid @RequestBody DisciplinaRequestDTO disciplinaDto) {
        DisciplinaResponseDTO disciplina = disciplinaService.createDisciplina(disciplinaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }

    @Operation(summary = "Lista todas as disciplinas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public List<DisciplinaResponseDTO> getAllDisciplinas(
            @RequestParam(defaultValue = "0") int paginaAtual,
            @RequestParam(defaultValue = "10") int tamanhoPagina
    ) {
        return disciplinaService.getAllDisciplinas(paginaAtual, tamanhoPagina);
    }

    @Operation(summary = "Busca disciplina por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disciplina encontrada"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> getDisciplina(@PathVariable UUID id) {
        DisciplinaResponseDTO disciplina = disciplinaService.getDisciplina(id);
        return ResponseEntity.ok(disciplina);
    }

    @Operation(summary = "Exclui uma disciplina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disciplina excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable UUID id) {
        disciplinaService.deleteDisciplina(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza parcialmente uma disciplina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> patchDisciplina(@PathVariable UUID id, @RequestBody DisciplinaRequestDTO dto) {
        return ResponseEntity.ok(disciplinaService.patchDisciplina(id, dto));
    }
}
