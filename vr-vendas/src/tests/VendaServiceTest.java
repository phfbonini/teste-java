package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import service.DatabaseService;
import service.VendaService;
import model.Venda;
import model.ItemVenda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaServiceTest {
    private int testVendaId;
    private Connection connection;

    @Before
    public void setUp() {
        DatabaseService.connectToDatabase();
        connection = DatabaseService.getConnection();

        Venda vendaTeste = criarVendaDeTeste();
        List<ItemVenda> itensVendaTeste = criarItensDeVendaDeTeste();
        VendaService vendaService = new VendaService();

        try {
            vendaService.cadastrarVenda(vendaTeste, itensVendaTeste);
            testVendaId = getTestVendaId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        VendaService vendaService = new VendaService();
        vendaService.estornarVenda(testVendaId);

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCadastrarVenda() {

        assertNotNull(testVendaId);
    }

    @Test
    public void testGetAllVendas() {
        List<Venda> vendas = VendaService.getAllVendas();
        assertNotNull(vendas);
        assertFalse(vendas.isEmpty());
    }

    @Test
    public void testGetItensVenda() {
        List<ItemVenda> itensVenda = VendaService.getItensVenda(testVendaId);
        assertNotNull(itensVenda);
        assertFalse(itensVenda.isEmpty());
    }

    @Test
    public void testGetVendaById() {
        Venda venda = VendaService.getVendaById(testVendaId);
        assertNotNull(venda);
    }

    @Test
    public void testEstornarVenda() {
        VendaService vendaService = new VendaService();
        vendaService.estornarVenda(testVendaId);

        Venda vendaEstornada = VendaService.getVendaById(testVendaId);
        assertNotNull(vendaEstornada);
        assertEquals("estornada", vendaEstornada.getStatus());
    }

    private Venda criarVendaDeTeste() {
        Date data = new Date();
        int clienteId = 1; // ID do cliente de teste
        double valorTotal = 100.0;

        return new Venda(0, data, clienteId, valorTotal, "efetivada");
    }

    private List<ItemVenda> criarItensDeVendaDeTeste() {
        List<ItemVenda> itensVenda = new ArrayList<>();

        ItemVenda item1 = new ItemVenda(0, 1, 10, 10.0, 100.0); // Produto de teste
        ItemVenda item2 = new ItemVenda(0, 2, 5, 15.0, 75.0);   // Outro produto de teste

        itensVenda.add(item1);
        itensVenda.add(item2);

        return itensVenda;
    }

    private int getTestVendaId() {
        try {
            String sql = "SELECT id FROM vendas WHERE status = 'efetivada'";
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
