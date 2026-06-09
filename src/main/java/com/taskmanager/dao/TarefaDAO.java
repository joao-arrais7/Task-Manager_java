package com.taskmanager.dao;

import com.taskmanager.db.Conexao;
import com.taskmanager.model.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements GenericDAO<Tarefa> {

    private final Connection conn;

    public TarefaDAO() {
        this.conn = Conexao.getConexao();
        criarTabela();
    }

    // Cria a tabela se não existir
    private void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tarefas (
                    id        INT PRIMARY KEY AUTO_INCREMENT,
                    titulo    VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    concluida BOOLEAN NOT NULL DEFAULT FALSE
                )
                """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    @Override
    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, concluida) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setBoolean(3, tarefa.isConcluida());
            ps.executeUpdate();
            System.out.println("✅ Tarefa inserida: " + tarefa.getTitulo());
        } catch (SQLException e) {
            System.err.println("Erro ao inserir: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET titulo=?, descricao=?, concluida=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setBoolean(3, tarefa.isConcluida());
            ps.setInt(4, tarefa.getId());
            ps.executeUpdate();
            System.out.println("✅ Tarefa atualizada: " + tarefa.getTitulo());
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM tarefas WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("🗑️ Tarefa deletada: id=" + id);
        } catch (SQLException e) {
            System.err.println("Erro ao deletar: " + e.getMessage());
        }
    }

    @Override
    public Tarefa buscarPorId(int id) {
        String sql = "SELECT * FROM tarefas WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Tarefa> listarTodos() {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas ORDER BY id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    // Busca por título (para o campo de busca da tela)
    public List<Tarefa> buscarPorTitulo(String titulo) {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas WHERE titulo LIKE ? ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por título: " + e.getMessage());
        }
        return lista;
    }

    // Converte ResultSet em objeto Tarefa
    private Tarefa mapear(ResultSet rs) throws SQLException {
        return new Tarefa(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descricao"),
                rs.getBoolean("concluida")
        );
    }
}