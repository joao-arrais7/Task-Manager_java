package com.taskmanager;

import com.taskmanager.dao.GenericDAO;
import com.taskmanager.model.Tarefa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Versão do DAO que aceita conexão injetada (para testes)
public class TarefaDAOTestHelper implements GenericDAO<Tarefa> {

    private final Connection conn;

    public TarefaDAOTestHelper(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, concluida) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            ps.setBoolean(3, tarefa.isConcluida());
            ps.executeUpdate();
        } catch (SQLException e) {
            fail("Erro ao inserir: " + e.getMessage());
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
        } catch (SQLException e) {
            fail("Erro ao atualizar: " + e.getMessage());
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM tarefas WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            fail("Erro ao deletar: " + e.getMessage());
        }
    }

    @Override
    public Tarefa buscarPorId(int id) {
        String sql = "SELECT * FROM tarefas WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            fail("Erro ao buscar: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Tarefa> listarTodos() {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas ORDER BY id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            fail("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public List<Tarefa> buscarPorTitulo(String titulo) {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas WHERE titulo LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            fail("Erro ao buscar por título: " + e.getMessage());
        }
        return lista;
    }

    private Tarefa mapear(ResultSet rs) throws SQLException {
        return new Tarefa(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descricao"),
                rs.getBoolean("concluida")
        );
    }

    private void fail(String msg) {
        throw new RuntimeException(msg);
    }
}