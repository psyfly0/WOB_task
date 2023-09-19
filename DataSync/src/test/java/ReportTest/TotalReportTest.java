/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportTest;

import hu.datasynccli.datasync.exceptions.DataSyncException;
import hu.datasynccli.datasync.report.MonthlyReportAmazon;
import hu.datasynccli.datasync.report.MonthlyReportEbay;
import hu.datasynccli.datasync.report.TotalReport;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author szaboa
 */
public class TotalReportTest {
    
    public TotalReportTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCalculateTotalEbayCount() throws DataSyncException {
        MonthlyReportEbay monthlyReportEbay = mock(MonthlyReportEbay.class);
        
        TotalReport totalReport = new TotalReport(mock(Connection.class), monthlyReportEbay, null);

        TreeMap<String, Integer> ebayCounts = new TreeMap<>();
        ebayCounts.put("2023-08", 10);
        when(monthlyReportEbay.calculateTotalEbayListingCount()).thenReturn(ebayCounts);

        int totalEbayCount = totalReport.calculateTotalEbayCount();

        assertEquals(10, totalEbayCount);
    }

    @Test
    public void testCalculateTotalAmazonCount() throws DataSyncException {
        MonthlyReportAmazon monthlyReportAmazon = mock(MonthlyReportAmazon.class);
        
        TotalReport totalReport = new TotalReport(mock(Connection.class), null, monthlyReportAmazon);

        TreeMap<String, Integer> amazonCounts = new TreeMap<>();
        amazonCounts.put("2023-08", 15);
        when(monthlyReportAmazon.calculateTotalAmazonListingCount()).thenReturn(amazonCounts);

        int totalAmazonCount = totalReport.calculateTotalAmazonCount();

        assertEquals(15, totalAmazonCount);
    }
    
    @Test
    public void testCalculateTotalListingCount() throws DataSyncException {
        // Ebay
        MonthlyReportEbay monthlyReportEbay = mock(MonthlyReportEbay.class);
        
        TotalReport totalReportEbay = new TotalReport(mock(Connection.class), monthlyReportEbay, null);

        TreeMap<String, Integer> ebayCounts = new TreeMap<>();
        ebayCounts.put("2023-08", 10);
        when(monthlyReportEbay.calculateTotalEbayListingCount()).thenReturn(ebayCounts);

        int totalEbayCount = totalReportEbay.calculateTotalEbayCount();
        
        // Amazon
        MonthlyReportAmazon monthlyReportAmazon = mock(MonthlyReportAmazon.class);
        
        TotalReport totalReportAmazon = new TotalReport(mock(Connection.class), null, monthlyReportAmazon);

        TreeMap<String, Integer> amazonCounts = new TreeMap<>();
        amazonCounts.put("2023-08", 15);
        when(monthlyReportAmazon.calculateTotalAmazonListingCount()).thenReturn(amazonCounts);

        int totalAmazonCount = totalReportAmazon.calculateTotalAmazonCount();
        
        int result = totalEbayCount + totalAmazonCount;
        
        assertEquals(25, result);
    }
    
    @Test
    public void testCalculateTotalEbayListingPrice() throws DataSyncException {
        MonthlyReportEbay monthlyReportEbay = mock(MonthlyReportEbay.class);
        
        TotalReport totalReport = new TotalReport(mock(Connection.class), monthlyReportEbay, null);

        TreeMap<String, BigDecimal> ebayCounts = new TreeMap<>();
        BigDecimal price = new BigDecimal("100.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        
        ebayCounts.put("2023-08", price);
        when(monthlyReportEbay.calculateTotalEbayListingPrice()).thenReturn(ebayCounts);

        BigDecimal totalEbayPrice = totalReport.calculateTotalEbayListingPrice();

        assertEquals(new BigDecimal("100.00"), totalEbayPrice);
    }
    
    @Test
    public void testCalculateAverageEbayListingPrice() throws DataSyncException {
        // Price
        MonthlyReportEbay monthlyReportEbay = mock(MonthlyReportEbay.class);
        
        TotalReport totalReportPrice = new TotalReport(mock(Connection.class), monthlyReportEbay, null);

        TreeMap<String, BigDecimal> ebayPrice = new TreeMap<>();
        BigDecimal price = new BigDecimal("100.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        
        ebayPrice.put("2023-08", price);
        when(monthlyReportEbay.calculateTotalEbayListingPrice()).thenReturn(ebayPrice);

        BigDecimal totalEbayPrice = totalReportPrice.calculateTotalEbayListingPrice();
        
        // Count     
        TotalReport totalReportCount = new TotalReport(mock(Connection.class), monthlyReportEbay, null);

        TreeMap<String, Integer> ebayCounts = new TreeMap<>();
        ebayCounts.put("2023-08", 10);
        when(monthlyReportEbay.calculateTotalEbayListingCount()).thenReturn(ebayCounts);

        int totalEbayCount = totalReportCount.calculateTotalEbayCount();
        
        BigDecimal result = totalEbayPrice.divide(BigDecimal.valueOf(totalEbayCount), RoundingMode.HALF_UP);
        
        assertEquals(new BigDecimal("10.00"), result);      
    }
    
    @Test
    public void testCalculateTotalAmazonListingPrice() throws DataSyncException {
        MonthlyReportAmazon monthlyReportAmazon = mock(MonthlyReportAmazon.class);
        
        TotalReport totalReport = new TotalReport(mock(Connection.class), null, monthlyReportAmazon);

        TreeMap<String, BigDecimal> amazonCounts = new TreeMap<>();
        BigDecimal price = new BigDecimal("100.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        
        amazonCounts.put("2023-08", price);
        when(monthlyReportAmazon.calculateTotalAmazonListingPrice()).thenReturn(amazonCounts);

        BigDecimal totalAmazonPrice = totalReport.calculateTotalAmazonListingPrice();

        assertEquals(new BigDecimal("100.00"), totalAmazonPrice); 
    }
    
    @Test
    public void testCalculateAverageAmazonListingPrice() throws DataSyncException {
        // Price
        MonthlyReportAmazon monthlyReportAmazon = mock(MonthlyReportAmazon.class);
        
        TotalReport totalReportPrice = new TotalReport(mock(Connection.class), null, monthlyReportAmazon);

        TreeMap<String, BigDecimal> amazonPrice = new TreeMap<>();
        BigDecimal price = new BigDecimal("100.00").setScale(2, BigDecimal.ROUND_HALF_UP);
        
        amazonPrice.put("2023-08", price);
        when(monthlyReportAmazon.calculateTotalAmazonListingPrice()).thenReturn(amazonPrice);

        BigDecimal totalAmazonPrice = totalReportPrice.calculateTotalAmazonListingPrice();
        
        // Count        
        TotalReport totalReportCount = new TotalReport(mock(Connection.class), null, monthlyReportAmazon);

        TreeMap<String, Integer> amazonCounts = new TreeMap<>();
        amazonCounts.put("2023-08", 15);
        when(monthlyReportAmazon.calculateTotalAmazonListingCount()).thenReturn(amazonCounts);

        int totalAmazonCount = totalReportCount.calculateTotalAmazonCount();
        
        BigDecimal result = totalAmazonPrice.divide(BigDecimal.valueOf(totalAmazonCount), RoundingMode.HALF_UP);
        
        assertEquals(new BigDecimal("6.67"), result);      
    }
    
    @Test
    public void testCalculateBestListerPerMonth() throws SQLException, DataSyncException {
        Connection mockedConnection = mock(Connection.class);
        PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockedResultSet = mock(ResultSet.class);

        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);

        when(mockedResultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(mockedResultSet.getString("month"))
                .thenReturn("2023-08")
                .thenReturn("2023-09")
                .thenReturn("2023-10")
                .thenReturn("2023-11");

        when(mockedResultSet.getString("best_lister"))
                .thenReturn("example@example.com")
                .thenReturn("best@best.com")
                .thenReturn("best@best.com")
                .thenReturn("anyone@anywhere.com");

        TotalReport totalReport = new TotalReport(mockedConnection, null, null);
        TreeMap<String, String> result = totalReport.calculateBestListerPerMonth();

        assertEquals(4, result.size());
        assertEquals("best@best.com", result.get("2023-09"));
        assertEquals("anyone@anywhere.com", result.get("2023-11"));
    }
    
    @Test
    public void testCalculateBestLister() throws SQLException, DataSyncException {
        Connection mockedConnection = mock(Connection.class);
        PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockedResultSet = mock(ResultSet.class);

        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);

        when(mockedResultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(mockedResultSet.getString("month"))
                .thenReturn("2023-08")
                .thenReturn("2023-09")
                .thenReturn("2023-10")
                .thenReturn("2023-11");

        when(mockedResultSet.getString("best_lister"))
                .thenReturn("example@example.com")
                .thenReturn("best@best.com")
                .thenReturn("best@best.com")
                .thenReturn("anyone@anywhere.com");

        TotalReport totalReport = new TotalReport(mockedConnection, null, null);
        String result = totalReport.calculateBestLister();
        
        assertEquals("best@best.com", result);
    }
    
}
