package com.taskmanager.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static Connection conexao;
    private static String url;
    private static String usuario;
    private static String senha;

    static {
        try (InputStream input = Conexao.class
                .getClassLoader()
                .getResourceAsStream("com/taskmanager/config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Arquivo config.properties não encontrado! " +
                                "Renomeie o config.properties.example para config.properties " +
                                "e preencha com suas credenciais."
                );
            }

            Properties prop = new Properties();
            prop.load(input);

            url      = prop.getProperty("db.url");
            usuario  = prop.getProperty("db.usuario");
            senha    = prop.getProperty("db.senha");

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }

    private Conexao() {}

    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(url, usuario, senha);
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