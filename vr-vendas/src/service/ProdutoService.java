package service;

import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {
    public static List<Produto> getAllProdutos() {
        List<Produto> produtos = new ArrayList<>();
        Connection connection = DatabaseService.getConnection();

        try {
            String sql = "SELECT * FROM produtos";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String descricao = resultSet.getString("descricao");
                double preco = resultSet.getDouble("preco");
                int quantidade = resultSet.getInt("quantidade");
                Produto produto = new Produto(id, descricao, preco, quantidade);
                produtos.add(produto);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public static void cadastrarProduto(String descricao, double preco, int quantidade) {
        Connection connection = DatabaseService.getConnection();

        try {
            String sql = "INSERT INTO produtos (descricao, preco, quantidade) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, descricao);
            statement.setDouble(2, preco);
            statement.setInt(3, quantidade);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
