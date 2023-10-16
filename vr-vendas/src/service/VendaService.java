package service;
import model.ItemVenda;
import model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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

    public static List<Venda> getAllVendas() {
        List<Venda> vendas = new ArrayList<>();

        try {
            String sql = "SELECT * FROM vendas";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date data = resultSet.getDate("data");
                int clienteId = resultSet.getInt("cliente_id");
                double valorTotal = resultSet.getDouble("valor_total");
                String status = resultSet.getString("status");

                Venda venda = new Venda(id, data, clienteId, valorTotal, status);
                vendas.add(venda);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendas;
    }

    public static List<ItemVenda> getItensVenda(int vendaId) {
        List<ItemVenda> itensVenda = new ArrayList<>();

        try {
            String sql = "SELECT id, produto_id, quantidade, preco, valor_total FROM itens_venda WHERE venda_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, vendaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int produtoId = resultSet.getInt("produto_id");
                int quantidade = resultSet.getInt("quantidade");
                double preco = resultSet.getDouble("preco");
                double valorTotal = resultSet.getDouble("valor_total");

                ItemVenda item = new ItemVenda(id, produtoId, quantidade, preco, valorTotal);
                itensVenda.add(item);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itensVenda;
    }

    public static Venda getVendaById(int vendaId) {
        Venda venda = null;

        try {
            String sql = "SELECT * FROM vendas WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, vendaId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date data = resultSet.getDate("data");
                int clienteId = resultSet.getInt("cliente_id");
                double valorTotal = resultSet.getDouble("valor_total");
                String status = resultSet.getString("status");

                venda = new Venda(id, data, clienteId, valorTotal, status);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return venda;
    }

    public void estornarVenda(int vendaId) {
        try {
            if (connection.isClosed()) {
                // Reabra a conexão com o banco de dados
                connection = DatabaseService.getConnection();
            }

            Venda venda = getVendaById(vendaId);


            if (!venda.getStatus().equals("estornada")) {
                List<ItemVenda> itensVenda = getItensVenda(vendaId);


                for (ItemVenda item : itensVenda) {
                    ProdutoService.atualizarQuantidadeProduto(item.getProdutoId(), -item.getQuantidade());
                }

                String sql = "UPDATE vendas SET status = 'estornada' WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, vendaId);
                statement.executeUpdate();
            } else {
                System.out.println("A venda já foi estornada anteriormente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

