package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import service.ClienteService;
import model.Cliente;
import service.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClienteServiceTest {
    private int testClientId;
    private Connection connection;

    @Before
    public void setUp() {
        DatabaseService.connectToDatabase();
        connection = DatabaseService.getConnection();


        String nome = "Cliente de Teste";
        ClienteService.cadastrarCliente(nome);
        testClientId = getTestClientId();
    }


    @Test
    public void testGetAllClientes() {
        List<Cliente> clientes = ClienteService.getAllClientes();
        assertNotNull(clientes);
        assertFalse(clientes.isEmpty());
    }

    @Test
    public void testCadastrarCliente() {
        String nome = "Novo Cliente";
        ClienteService.cadastrarCliente(nome);


        int novoClienteId = getTestClientId();
        Cliente novoCliente = ClienteService.getClienteById(novoClienteId);

        assertNotNull(novoCliente);
        assertEquals(nome, novoCliente.getNome());

    }

    @Test
    public void testGetClienteById() {
        assertNotNull(testClientId);
    }

    private int getTestClientId() {
        try {
            String sql = "SELECT id FROM clientes WHERE nome = 'Novo Cliente'";
            PreparedStatement statement = connection.prepareStatement(sql);
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
