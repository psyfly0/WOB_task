/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import hu.datasynccli.datasync.dao.ListingStatusDAO;
import hu.datasynccli.datasync.entity.ListingStatus;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class ListingStatusDAOTest {
    
    public ListingStatusDAOTest() {
    }
    
    @Test
    public void testInsertListings() throws DataSyncException, SQLException {
        Connection mockedConnection = mock(Connection.class);
        PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);

        ListingStatusDAO listingStatusDAO = new ListingStatusDAO(mockedConnection);

        List<ListingStatus> listingStatuses = new ArrayList<>();
        listingStatuses.add(new ListingStatus(1, "sold"));
        listingStatuses.add(new ListingStatus(2, "not sold"));

        listingStatusDAO.insertListings(listingStatuses);

        verify(mockedConnection).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(listingStatuses.size())).addBatch();
        verify(mockedPreparedStatement).executeBatch();
    }
}
