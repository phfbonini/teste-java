package tests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import service.DatabaseService;
import service.ProdutoService;
import model.Produto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProdutoServiceTest {
    private int testProductId;
    private Connection connection;

    @Before
    public void setUp() {
        DatabaseService.connectToDatabase();
        connection = DatabaseService.getConnection();
        ProdutoService.cadastrarProduto("Produto de Teste", 10.0, 100);
        testProductId = getTestProductId();
    }


    @Test
    public void testGetAllProdutos() {
        List<Produto> produtos = ProdutoService.getAllProdutos();
        assertNotNull(produtos);
        assertFalse(produtos.isEmpty());
    }

    @Test
    public void testCadastrarProduto() {
        String descricao = "Produto de Teste";
        double preco = 10.0;
        int quantidade = 100;
        ProdutoService.cadastrarProduto(descricao, preco, quantidade);


        int novoProdutoId = getTestProductId();
        Produto novoProduto = ProdutoService.getProdutoById(novoProdutoId);

        assertNotNull(novoProduto);
        assertEquals(descricao, novoProduto.getDescricao());
        assertEquals(preco, novoProduto.getPreco(), 0.001);
        assertEquals(quantidade, novoProduto.getQuantidade());

    }

    @Test
    public void testGetProdutoById() {
        Produto produto = ProdutoService.getProdutoById(testProductId);
        assertNotNull(produto);
    }

    @Test
    public void testAtualizarQuantidadeProduto() throws SQLException {
        int quantidadeVendida = 20;
        ProdutoService.atualizarQuantidadeProduto(testProductId, quantidadeVendida);

        Produto produto = ProdutoService.getProdutoById(testProductId);
        assertNotNull(produto);
        assertEquals(80, produto.getQuantidade());
    }

    private int getTestProductId() {
        try {
            String sql = "SELECT id FROM produtos WHERE descricao = 'Produto de Teste'";
            java.sql.PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
