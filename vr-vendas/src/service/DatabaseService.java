package service;

import java.sql.*;

public class DatabaseService {
    private static Connection connection;

    public static void connectToDatabase() {
        try {
            String url = "jdbc:postgresql://localhost:5432/TesteDb";
            String user = "root";
            String password = "admin";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }



    public static Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        connectToDatabase();

        // Verifique se a conexão está funcionando
        if (connection != null) {
            System.out.println("Conexão com o banco de dados está ativa.");
        } else {
            System.out.println("Conexão com o banco de dados não está ativa.");
        }
        
    }
}
