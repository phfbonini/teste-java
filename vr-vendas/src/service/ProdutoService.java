package service;

import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private static Connection connection = DatabaseService.getConnection();
    public static List<Produto> getAllProdutos() {
        List<Produto> produtos = new ArrayList<>();


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

    public static Produto getProdutoById(int id) {
        Connection connection = DatabaseService.getConnection();
        Produto produto = null;

        try {
            String sql = "SELECT * FROM produtos WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String descricao = resultSet.getString("descricao");
                double preco = resultSet.getDouble("preco");
                int quantidade = resultSet.getInt("quantidade");
                produto = new Produto(id, descricao, preco, quantidade);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public static void atualizarQuantidadeProduto(int produtoId, int quantidadeVendida) throws SQLException {
        String selectSql = "SELECT quantidade FROM produtos WHERE id = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        selectStatement.setInt(1, produtoId);
        ResultSet resultSet = selectStatement.executeQuery();

        int quantidadeAtual = 0;

        if (resultSet.next()) {
            quantidadeAtual = resultSet.getInt("quantidade");
        } else {
        }

        resultSet.close();
        selectStatement.close();

        int novaQuantidade = quantidadeAtual - quantidadeVendida;

        String updateSql = "UPDATE produtos SET quantidade = ? WHERE id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
        updateStatement.setInt(1, novaQuantidade);
        updateStatement.setInt(2, produtoId);
        updateStatement.executeUpdate();

        updateStatement.close();
    }
}
