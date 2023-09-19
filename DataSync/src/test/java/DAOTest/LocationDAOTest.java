/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import hu.datasynccli.datasync.dao.LocationDAO;
import hu.datasynccli.datasync.entity.Location;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author szaboa
 */
public class LocationDAOTest {
    
    public LocationDAOTest() {
    }
    
        @Test
    public void testInsertListings() throws DataSyncException, SQLException {
        Connection mockedConnection = mock(Connection.class);
        PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);

        LocationDAO locationDAO = new LocationDAO(mockedConnection);
        
        UUID id10 = UUID.randomUUID();
        UUID id20 = UUID.randomUUID();

        List<Location> locations = new ArrayList<>();
        locations.add(new Location(id10, "manager", "+363232", "add1", "add2", "HUN", "BP", "1098"));
        locations.add(new Location(id20, "manager2", "+36575", "street", "road", "GER", "BE", "65654"));

        locationDAO.insertListings(locations);

        verify(mockedConnection).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(locations.size())).addBatch();
        verify(mockedPreparedStatement).executeBatch();
    }
}
