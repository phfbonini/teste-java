package service;

import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    public static List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Connection connection = DatabaseService.getConnection();

        try {
            String sql = "SELECT * FROM clientes";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                Cliente cliente = new Cliente(id, nome);
                clientes.add(cliente);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public static void cadastrarCliente(String nome) {
        Connection connection = DatabaseService.getConnection();

        try {
            String sql = "INSERT INTO clientes (nome) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Cliente getClienteById(int clienteId) {
        Connection connection = DatabaseService.getConnection();
        Cliente cliente = null;

        try {
            String sql = "SELECT * FROM clientes WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clienteId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                cliente = new Cliente(id, nome);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }
}
