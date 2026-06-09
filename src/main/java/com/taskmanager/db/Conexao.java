package com.taskmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // ⚠️ Altere usuário e senha conforme seu MySQL
    private static final String URL    = "jdbc:mysql://localhost:3306/taskmanager_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA   = "0208";

    private static Connection conexao;

    // Impede instanciação
    private Conexao() {}

    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("✅ Conectado ao banco de dados!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar: " + e.getMessage());
            throw new RuntimeException("Falha na conexão com o banco.", e);
        }
        return conexao;
    }

    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("🔌 Conexão encerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}