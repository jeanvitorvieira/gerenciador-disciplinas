package com.example.gerenciadordisciplinas.repositories;

import com.example.gerenciadordisciplinas.models.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DisciplinaRepository extends JpaRepository<Disciplina, UUID> {
    boolean existsByNomeDisciplina(String nomeDisciplina);
}
