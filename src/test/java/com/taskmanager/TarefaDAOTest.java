package com.taskmanager;

import com.taskmanager.model.Tarefa;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do TarefaDAO com SQLite em memória")
public class TarefaDAOTest {

    // Conexão SQLite em memória — não toca no MySQL real
    private Connection conn;
    private TarefaDAOTestHelper dao;

    @BeforeEach
    void setUp() throws SQLException {
        // Cria banco em memória do zero antes de cada teste
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        criarTabela();
        dao = new TarefaDAOTestHelper(conn);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // Cria a tabela no banco em memória
    private void criarTabela() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS tarefas (
                    id        INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo    VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    concluida BOOLEAN NOT NULL DEFAULT 0
                )
                """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // ── Testes de inserção ─────────────────────────────────────────

    @Test
    @DisplayName("Deve inserir uma tarefa no banco")
    void deveInserirTarefa() {
        Tarefa tarefa = new Tarefa("Tarefa Teste", "Descrição", false);
        dao.inserir(tarefa);

        List<Tarefa> lista = dao.listarTodos();
        assertEquals(1, lista.size());
        assertEquals("Tarefa Teste", lista.get(0).getTitulo());
    }

    @Test
    @DisplayName("Deve inserir múltiplas tarefas")
    void deveInserirMultiplasTarefas() {
        dao.inserir(new Tarefa("Tarefa 1", "Desc 1", false));
        dao.inserir(new Tarefa("Tarefa 2", "Desc 2", true));
        dao.inserir(new Tarefa("Tarefa 3", "Desc 3", false));

        assertEquals(3, dao.listarTodos().size());
    }

    // ── Testes de listagem ─────────────────────────────────────────

    @Test
    @DisplayName("Deve retornar lista vazia quando não há tarefas")
    void deveRetornarListaVaziaQuandoSemTarefas() {
        List<Tarefa> lista = dao.listarTodos();
        assertTrue(lista.isEmpty());
    }

    @Test
    @DisplayName("Deve listar todas as tarefas corretamente")
    void deveListarTodasAsTarefas() {
        dao.inserir(new Tarefa("Tarefa A", "Desc A", false));
        dao.inserir(new Tarefa("Tarefa B", "Desc B", true));

        List<Tarefa> lista = dao.listarTodos();
        assertEquals(2, lista.size());
    }

    // ── Testes de busca ────────────────────────────────────────────

    @Test
    @DisplayName("Deve buscar tarefa por ID")
    void deveBuscarTarefaPorId() {
        dao.inserir(new Tarefa("Tarefa Busca", "Desc", false));
        List<Tarefa> lista = dao.listarTodos();

        int id = lista.get(0).getId();
        Tarefa encontrada = dao.buscarPorId(id);

        assertNotNull(encontrada);
        assertEquals("Tarefa Busca", encontrada.getTitulo());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar ID inexistente")
    void deveRetornarNullParaIdInexistente() {
        Tarefa tarefa = dao.buscarPorId(999);
        assertNull(tarefa);
    }

    @Test
    @DisplayName("Deve buscar tarefa por título parcial")
    void deveBuscarTarefaPorTitulo() {
        dao.inserir(new Tarefa("Estudar Java", "Desc", false));
        dao.inserir(new Tarefa("Estudar Python", "Desc", false));
        dao.inserir(new Tarefa("Fazer exercício", "Desc", false));

        List<Tarefa> resultado = dao.buscarPorTitulo("Estudar");
        assertEquals(2, resultado.size());
    }

    // ── Testes de atualização ──────────────────────────────────────

    @Test
    @DisplayName("Deve atualizar tarefa corretamente")
    void deveAtualizarTarefa() {
        dao.inserir(new Tarefa("Título Original", "Desc", false));
        Tarefa tarefa = dao.listarTodos().get(0);

        tarefa.setTitulo("Título Atualizado");
        tarefa.setConcluida(true);
        dao.atualizar(tarefa);

        Tarefa atualizada = dao.buscarPorId(tarefa.getId());
        assertEquals("Título Atualizado", atualizada.getTitulo());
        assertTrue(atualizada.isConcluida());
    }

    @Test
    @DisplayName("Deve marcar tarefa como concluída")
    void deveMarcarTarefaComoConcluida() {
        dao.inserir(new Tarefa("Tarefa", "Desc", false));
        Tarefa tarefa = dao.listarTodos().get(0);

        tarefa.setConcluida(true);
        dao.atualizar(tarefa);

        assertTrue(dao.buscarPorId(tarefa.getId()).isConcluida());
    }

    // ── Testes de remoção ──────────────────────────────────────────

    @Test
    @DisplayName("Deve deletar tarefa por ID")
    void deveDeletarTarefa() {
        dao.inserir(new Tarefa("Deletar", "Desc", false));
        Tarefa tarefa = dao.listarTodos().get(0);

        dao.deletar(tarefa.getId());

        assertEquals(0, dao.listarTodos().size());
    }

    @Test
    @DisplayName("Não deve afetar outras tarefas ao deletar uma")
    void naoDeveAfestarOutrasTarefasAoDeletar() {
        dao.inserir(new Tarefa("Manter",  "Desc", false));
        dao.inserir(new Tarefa("Deletar", "Desc", false));

        Tarefa deletar = dao.listarTodos().stream()
                .filter(t -> t.getTitulo().equals("Deletar"))
                .findFirst().orElseThrow();

        dao.deletar(deletar.getId());

        List<Tarefa> lista = dao.listarTodos();
        assertEquals(1, lista.size());
        assertEquals("Manter", lista.get(0).getTitulo());
    }
}