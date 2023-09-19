/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtils;

import hu.datasynccli.datasync.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author szaboa
 */
public class DatabaseConnectionTest {
    
    public DatabaseConnectionTest() {
    }
    
    @Test
    public void testDatabaseConnection() {
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull("Database connection should not be null", connection);
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
