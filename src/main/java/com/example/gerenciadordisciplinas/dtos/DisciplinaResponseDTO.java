package com.example.gerenciadordisciplinas.dtos;

import java.util.UUID;

public record DisciplinaResponseDTO(
        UUID id,
        String nomeDisciplina,
        String professorDisciplina
) {

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public String nomeDisciplina() {
        return nomeDisciplina;
    }

    @Override
    public String professorDisciplina() {
        return professorDisciplina;
    }
}
