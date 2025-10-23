package com.example.gerenciadordisciplinas;

import com.example.gerenciadordisciplinas.dtos.DisciplinaRequestDTO;
import com.example.gerenciadordisciplinas.dtos.DisciplinaResponseDTO;
import com.example.gerenciadordisciplinas.exceptions.ConflictException;
import com.example.gerenciadordisciplinas.exceptions.NotFoundException;
import com.example.gerenciadordisciplinas.services.DisciplinaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DisciplinaServiceIntegrationTests {

	@Autowired
	private DisciplinaService disciplinaService;

	private DisciplinaResponseDTO createAndSaveDisciplina(String nome, String professor) {
		DisciplinaRequestDTO novaDisciplina = new DisciplinaRequestDTO(nome, professor);
		return disciplinaService.createDisciplina(novaDisciplina);
	}

	@Test
	void deveSalvarEDisciplinaERetornarPorId() {
		DisciplinaRequestDTO novaDisciplina = new DisciplinaRequestDTO("Backend", "Ramon");

		DisciplinaResponseDTO disciplinaSalva = disciplinaService.createDisciplina(novaDisciplina);

		assertNotNull(disciplinaSalva.id());

		DisciplinaResponseDTO disciplinaEncontrada = disciplinaService.getDisciplina(disciplinaSalva.id());

		assertEquals("Backend", disciplinaEncontrada.nomeDisciplina());
		assertEquals("Ramon", disciplinaEncontrada.professorDisciplina());
	}

	@Test
	void deveLancarNotFoundExceptionAoBuscarIdInexistente() {
		assertThrows(NotFoundException.class, () -> {
			disciplinaService.getDisciplina(UUID.randomUUID());
		});
	}

	@Test
	void deveExcluirDisciplinaComSucesso() {
		DisciplinaResponseDTO disciplinaSalva = createAndSaveDisciplina("Cálculo II", "Beatriz");
		UUID id = disciplinaSalva.id();

		disciplinaService.deleteDisciplina(id);

		assertThrows(NotFoundException.class, () -> {
			disciplinaService.getDisciplina(id);
		});
	}

	@Test
	void deveLancarNotFoundExceptionAoExcluirIdInexistente() {
		assertThrows(NotFoundException.class, () -> {
			disciplinaService.deleteDisciplina(UUID.randomUUID());
		});
	}

	@Test
	void deveAtualizarDisciplinaComSucesso() {
		DisciplinaResponseDTO disciplinaOriginal = createAndSaveDisciplina("Front-end Básico", "Clara");
		UUID id = disciplinaOriginal.id();

		DisciplinaRequestDTO dtoAtualizacao = new DisciplinaRequestDTO("Front-end Avançado", "Pedro");
		DisciplinaResponseDTO disciplinaAtualizada = disciplinaService.patchDisciplina(id, dtoAtualizacao);

		assertEquals(id, disciplinaAtualizada.id());
		assertEquals("Front-end Avançado", disciplinaAtualizada.nomeDisciplina());
		assertEquals("Pedro", disciplinaAtualizada.professorDisciplina());
	}

	@Test
	void deveLancarNotFoundExceptionAoAtualizarIdInexistente() {
		DisciplinaRequestDTO dtoAtualizacao = new DisciplinaRequestDTO("Novo Nome", "Novo Professor");

		assertThrows(NotFoundException.class, () -> {
			disciplinaService.patchDisciplina(UUID.randomUUID(), dtoAtualizacao);
		});
	}

	@Test
	void deveLancarConflictExceptionAoTentarCriarDisciplinaComConflito() {
		createAndSaveDisciplina("Banco de Dados", "Lucas");

		DisciplinaRequestDTO dtoConflito = new DisciplinaRequestDTO("Banco de Dados", "Outro Professor");

		assertThrows(ConflictException.class, () -> {
			disciplinaService.createDisciplina(dtoConflito);
		});
	}
}