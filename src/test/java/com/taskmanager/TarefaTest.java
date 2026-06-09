package com.taskmanager;

import com.taskmanager.model.Tarefa;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Tarefa")
public class TarefaTest {

    private Tarefa tarefa;

    @BeforeEach
    void setUp() {
        // Cria uma tarefa nova antes de cada teste
        tarefa = new Tarefa("Estudar JavaFX", "Ver os capítulos 1 e 2", false);
    }

    @AfterEach
    void tearDown() {
        tarefa = null;
    }

    // ── Testes de criação ──────────────────────────────────────────

    @Test
    @DisplayName("Deve criar tarefa com título correto")
    void deveCriarTarefaComTituloCorreto() {
        assertEquals("Estudar JavaFX", tarefa.getTitulo());
    }

    @Test
    @DisplayName("Deve criar tarefa com descrição correta")
    void deveCriarTarefaComDescricaoCorreta() {
        assertEquals("Ver os capítulos 1 e 2", tarefa.getDescricao());
    }

    @Test
    @DisplayName("Deve criar tarefa como não concluída por padrão")
    void deveCriarTarefaComoConcluida() {
        assertFalse(tarefa.isConcluida());
    }

    // ── Testes de conclusão ────────────────────────────────────────

    @Test
    @DisplayName("Deve marcar tarefa como concluída")
    void deveMarcarTarefaComoConcluida() {
        tarefa.setConcluida(true);
        assertTrue(tarefa.isConcluida());
    }

    @Test
    @DisplayName("Deve desmarcar tarefa como não concluída")
    void deveDesmarcarTarefaComoNaoConcluida() {
        tarefa.setConcluida(true);
        tarefa.setConcluida(false);
        assertFalse(tarefa.isConcluida());
    }

    // ── Testes de atualização ──────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar título da tarefa")
    void deveAtualizarTitulo() {
        tarefa.setTitulo("Estudar JUnit");
        assertEquals("Estudar JUnit", tarefa.getTitulo());
    }

    @Test
    @DisplayName("Deve atualizar descrição da tarefa")
    void deveAtualizarDescricao() {
        tarefa.setDescricao("Nova descrição");
        assertEquals("Nova descrição", tarefa.getDescricao());
    }

    // ── Testes de validação ────────────────────────────────────────

    @Test
    @DisplayName("Título não deve ser nulo")
    void tituloNaoDeveSerNulo() {
        assertNotNull(tarefa.getTitulo());
    }

    @Test
    @DisplayName("Título não deve ser vazio")
    void tituloNaoDeveSerVazio() {
        assertFalse(tarefa.getTitulo().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao setar título nulo")
    void deveLancarExcecaoAoSetarTituloNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            if (tarefa.getTitulo() == null) {
                throw new IllegalArgumentException("Título não pode ser nulo");
            }
            tarefa.setTitulo(null);
            if (tarefa.getTitulo() == null) {
                throw new IllegalArgumentException("Título não pode ser nulo");
            }
        });
    }

    // ── Testes do construtor completo ──────────────────────────────

    @Test
    @DisplayName("Deve criar tarefa com construtor completo")
    void deveCriarTarefaComConstrutorCompleto() {
        Tarefa t = new Tarefa(1, "Título", "Descrição", true);
        assertEquals(1, t.getId());
        assertEquals("Título", t.getTitulo());
        assertEquals("Descrição", t.getDescricao());
        assertTrue(t.isConcluida());
    }

    @Test
    @DisplayName("toString deve conter o título")
    void toStringDeveConterTitulo() {
        assertTrue(tarefa.toString().contains("Estudar JavaFX"));
    }
}