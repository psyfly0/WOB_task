/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import hu.datasynccli.datasync.dao.MarketplaceDAO;
import hu.datasynccli.datasync.entity.Marketplace;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.mockito.Mockito.*;
     

/**
 *
 * @author szaboa
 */
public class MarketplaceDAOTest {
    
    public MarketplaceDAOTest() {
    }

   @Test
    public void testInsertListings() throws DataSyncException, SQLException {
        Connection mockedConnection = mock(Connection.class);
        PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);

        MarketplaceDAO marketplaceDAO = new MarketplaceDAO(mockedConnection);

        List<Marketplace> marketplaces = new ArrayList<>();
        marketplaces.add(new Marketplace(1, "Amazon"));
        marketplaces.add(new Marketplace(2, "eBay"));

        marketplaceDAO.insertListings(marketplaces);

        verify(mockedConnection).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(marketplaces.size())).addBatch();
        verify(mockedPreparedStatement).executeBatch();
    }
}
