package tests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import service.DatabaseService;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseServiceTest {
    private Connection connection;

    @Before
    public void setUp() {
        DatabaseService.connectToDatabase();
        connection = DatabaseService.getConnection();
    }

    @After
    public void tearDown() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectToDatabase() {
        assertNotNull(connection);
        try {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateTables() {

        DatabaseService.createTables();

        assertTrue(tableExists("produtos"));
        assertTrue(tableExists("clientes"));
        assertTrue(tableExists("vendas"));
        assertTrue(tableExists("itens_venda"));
    }

    private boolean tableExists(String tableName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT tablename FROM pg_catalog.pg_tables WHERE tablename = '" + tableName + "'");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

