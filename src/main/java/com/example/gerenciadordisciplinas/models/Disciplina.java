package com.example.gerenciadordisciplinas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nomeDisciplina;
    private String professorDisciplina;

    public Disciplina() {
    }

    public Disciplina(String nomeDisciplina, String professorDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
        this.professorDisciplina = professorDisciplina;
    }

    public UUID getId() {
        return id;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getProfessorDisciplina() {
        return professorDisciplina;
    }

    public void setProfessorDisciplina(String professorDisciplina) {
        this.professorDisciplina = professorDisciplina;
    }

    public void setUniversidade(String universidade) {
    }
}
