package com.example.gerenciadordisciplinas.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DisciplinaRequestDTO(
        @NotNull(message = "O nome da disciplina não pode ser nulo")
        @Size(min = 5, max = 100, message = "O nome da disciplina precisa ter entre 5 e 100 caracteres")
        String nomeDisciplina,

        @NotNull(message = "O nome do professor não pode ser nulo")
        @Size(min = 5, max = 100, message = "O nome do professor precisa ter entre 5 e 100 caracteres")
        String professorDisciplina
) {
}
