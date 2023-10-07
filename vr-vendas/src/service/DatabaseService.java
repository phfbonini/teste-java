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

    public static void createTables() {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();

            // Verifica se as tabelas já existem
            ResultSet resultSet = statement.executeQuery("SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname = 'public'");
            boolean produtosTableExists = false;
            boolean clientesTableExists = false;
            boolean vendasTableExists = false;
            boolean itensVendaTableExists = false;

            while (resultSet.next()) {
                String tableName = ((ResultSet) resultSet).getString("tablename");
                if (tableName.equals("produtos")) {
                    produtosTableExists = true;
                } else if (tableName.equals("clientes")) {
                    clientesTableExists = true;
                } else if (tableName.equals("vendas")) {
                    vendasTableExists = true;
                } else if (tableName.equals("itens_venda")) {
                    itensVendaTableExists = true;
                }
            }

            // Crie as tabelas que não existem
            if (!produtosTableExists) {
                statement.executeUpdate("CREATE TABLE produtos (id serial PRIMARY KEY, descricao VARCHAR(255), preco NUMERIC, quantidade INTEGER)");
            }

            if (!clientesTableExists) {
                statement.executeUpdate("CREATE TABLE clientes (id serial PRIMARY KEY, nome VARCHAR(255))");
            }

            if (!vendasTableExists) {
                statement.executeUpdate("CREATE TABLE vendas (id serial PRIMARY KEY, data DATE, cliente_id INTEGER, valor_total NUMERIC, status VARCHAR(255))");
            }

            if (!itensVendaTableExists) {
                statement.executeUpdate("CREATE TABLE itens_venda (id serial PRIMARY KEY, produto_id INTEGER, venda_id INTEGER, quantidade INTEGER, preco NUMERIC, valor_total NUMERIC)");
            }

            // Crie as relações entre as tabelas, se necessário
            if (!produtosTableExists || !vendasTableExists || !itensVendaTableExists) {
                // Crie chaves estrangeiras para vincular produtos, vendas e itens de venda
                statement.executeUpdate("ALTER TABLE itens_venda ADD FOREIGN KEY (produto_id) REFERENCES produtos (id)");
                statement.executeUpdate("ALTER TABLE itens_venda ADD FOREIGN KEY (venda_id) REFERENCES vendas (id)");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

        // Crie as tabelas quando a aplicação for iniciada
        createTables();
    }
}
