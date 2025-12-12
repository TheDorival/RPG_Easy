package br.com.tormenta20.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe para gerenciar conexões com o banco MySQL
 */
public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/tormenta20";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    
    // Pool de conexão simples (para produção, use HikariCP ou similar)
    private static Connection connection = null;
    
    /**
     * Obtém uma conexão com o banco de dados
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL não encontrado", e);
            }
        }
        return connection;
    }
    
    /**
     * Fecha a conexão
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}