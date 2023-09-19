/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import hu.datasynccli.datasync.dao.ListingDAO;
import hu.datasynccli.datasync.entity.Listing;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class ListingDAOTest {
    
    public ListingDAOTest() {
    }
    
    @Test
    public void testInsertListings() throws DataSyncException, SQLException {
        Connection mockedConnection = mock(Connection.class);
        PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);

        ListingDAO listingDAO = new ListingDAO(mockedConnection);
        
        UUID id10 = UUID.randomUUID();
        UUID id11 = UUID.randomUUID();
        UUID id20 = UUID.randomUUID();
        UUID id21 = UUID.randomUUID();
        
        BigDecimal price1 = new BigDecimal("100.00");
        BigDecimal price2 = new BigDecimal("200.00");
        
        LocalDate date1 = LocalDate.of(1999, 4, 24);
        LocalDate date2 = LocalDate.of(1969, 4, 24);

        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing(id10, "Book", "a good book", id11, price1, "HUF", 2, 1, 2, date1, "owner@book.com"));
        listings.add(new Listing(id20, "Book2", "a better book", id21, price2, "USD", 2, 1, 2, date2, "ff@ff.com"));

        listingDAO.insertListings(listings);

        verify(mockedConnection).prepareStatement(anyString());
        verify(mockedPreparedStatement, times(listings.size())).addBatch();
        verify(mockedPreparedStatement).executeBatch();
    }
}
