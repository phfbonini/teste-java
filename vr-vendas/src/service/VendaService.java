package service;
import model.ItemVenda;
import model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class VendaService {
    private static Connection connection;

    public VendaService() {
        connection = DatabaseService.getConnection();
    }
    
    public void cadastrarVenda(Venda venda, List<ItemVenda> itensVenda) throws SQLException {
        PreparedStatement vendaStatement = null;
        PreparedStatement itensVendaStatement = null;

        try {

            connection.setAutoCommit(false);

            String vendaSql = "INSERT INTO vendas (data, cliente_id, valor_total, status) VALUES (?, ?, ?, ?)";
            vendaStatement = connection.prepareStatement(vendaSql, Statement.RETURN_GENERATED_KEYS);
            vendaStatement.setDate(1, new java.sql.Date(venda.getData().getTime()));
            vendaStatement.setInt(2, venda.getClienteId());
            vendaStatement.setDouble(3, venda.getValorTotal());
            vendaStatement.setString(4, "efetivada");

            vendaStatement.executeUpdate();

            int vendaId;
            try (ResultSet generatedKeys = vendaStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vendaId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para a venda.");
                }
            }

            String itensVendaSql = "INSERT INTO itens_venda (produto_id, venda_id, quantidade, preco, valor_total) VALUES (?, ?, ?, ?, ?)";
            itensVendaStatement = connection.prepareStatement(itensVendaSql);

            for (ItemVenda item : itensVenda) {
                itensVendaStatement.setInt(1, item.getProdutoId());
                itensVendaStatement.setInt(2, vendaId);
                itensVendaStatement.setInt(3, item.getQuantidade());
                itensVendaStatement.setDouble(4, item.getPreco());
                itensVendaStatement.setDouble(5, item.getValorTotal());
                itensVendaStatement.addBatch();
            }

            itensVendaStatement.executeBatch();

            for (ItemVenda item : itensVenda) {
                int produtoId = item.getProdutoId();
                int quantidadeVendida = item.getQuantidade();
                ProdutoService.atualizarQuantidadeProduto(produtoId, quantidadeVendida);
            }

            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (vendaStatement != null) {
                vendaStatement.close();
            }
            if (itensVendaStatement != null) {
                itensVendaStatement.close();
            }
            assert connection != null;
            connection.setAutoCommit(true);
        }
    }



}

