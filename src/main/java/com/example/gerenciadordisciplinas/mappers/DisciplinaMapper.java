package com.example.gerenciadordisciplinas.mappers;

import com.example.gerenciadordisciplinas.dtos.DisciplinaResponseDTO;
import com.example.gerenciadordisciplinas.models.Disciplina;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaMapper {
    public DisciplinaResponseDTO toDto(Disciplina disciplina) {
        return new DisciplinaResponseDTO(
                disciplina.getId(),
                disciplina.getNomeDisciplina(),
                disciplina.getProfessorDisciplina()
        );
    }
}
