/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportTest;

import hu.datasynccli.datasync.exceptions.DataSyncException;
import hu.datasynccli.datasync.report.MonthlyReportEbay;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author szaboa
 */
@RunWith(MockitoJUnitRunner.class)
public class MonthlyReportEbayTest {
    
    public MonthlyReportEbayTest() {
    }
    
    @Mock
    private Connection mockedConnection;

    @Mock
    private PreparedStatement mockedPreparedStatement;

    @Mock
    private ResultSet mockedResultSet;

    @InjectMocks
    private MonthlyReportEbay monthlyReportEbay;
    
    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);

        when(mockedResultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
    }

    @Test
    public void testCalculateTotalEbayListingCount() throws SQLException, DataSyncException {
        when(mockedResultSet.getString("month")).thenReturn("2023-08", "2023-09");
        when(mockedResultSet.getInt("ebay_quantity")).thenReturn(100, 150);

        TreeMap<String, Integer> result = monthlyReportEbay.calculateTotalEbayListingCount();

        assertEquals(2, result.size());
        assertEquals(100, result.get("2023-08").intValue());
        assertEquals(150, result.get("2023-09").intValue());
    }
    
    @Test
    public void testCalculateTotalEbayListingPrice() throws SQLException, DataSyncException {
        BigDecimal price1 = new BigDecimal("100.00");
        BigDecimal price2 = new BigDecimal("200.00");
        
        when(mockedResultSet.getString("month")).thenReturn("2023-08", "2023-09");
        when(mockedResultSet.getBigDecimal("ebay_listing_price")).thenReturn(price1, price2);

        TreeMap<String, BigDecimal> result = monthlyReportEbay.calculateTotalEbayListingPrice();

        assertEquals(2, result.size());
        assertEquals(0, price1.compareTo(result.get("2023-08")));
        assertEquals(0, price2.compareTo(result.get("2023-09"))); 
    }
    
    @Test
    public void testCalculateAverageAmazonListingPrice() throws SQLException, DataSyncException {
        BigDecimal price1 = new BigDecimal("100.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal price2 = new BigDecimal("200.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        
        when(mockedResultSet.getString("month")).thenReturn("2023-08", "2023-09");
        when(mockedResultSet.getBigDecimal("ebay_listing_average")).thenReturn(price1, price2);

        TreeMap<String, BigDecimal> result = monthlyReportEbay.calculateAverageEbayListingPrice();

        assertEquals(2, result.size());
        assertEquals(0, price1.compareTo(result.get("2023-08")));
        assertEquals(0, price2.compareTo(result.get("2023-09"))); 
    } 
}
