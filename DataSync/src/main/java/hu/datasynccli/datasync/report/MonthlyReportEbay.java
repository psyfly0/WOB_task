/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.report;

import hu.datasynccli.datasync.dto.EbayMonthlyReportDTO;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

/**
 *
 * @author szaboa
 */
public class MonthlyReportEbay {
    private static final String SQL_MONTHS = "WITH months AS (" +
            "SELECT DATE_FORMAT(upload_time, '%Y-%m') AS month " +
            "FROM listing " +
            "GROUP BY month" +
            "), ";
    
    private Connection connection;
    
    public MonthlyReportEbay(Connection connection) {
        this.connection = connection;
    }
    
    public TreeMap<String, Integer> calculateTotalEbayListingCount() throws DataSyncException {
        TreeMap<String, Integer> ebayCounts = new TreeMap<>();
                String query = "ebay_listing_count AS (" +
                "SELECT " +
                    "m.month, " +
                    "IFNULL(SUM(CASE WHEN mp.marketplace_name = 'Ebay' THEN quantity ELSE 0 END), 0) AS ebay_quantity " +
                "FROM " +
                    "months m " +
                "LEFT JOIN " +
                    "listing l ON DATE_FORMAT(l.upload_time, '%Y-%m') = m.month " +
                "LEFT JOIN " +
                    "marketplace mp ON l.marketplace = mp.id " +
                "GROUP BY " +
                    "m.month " +
                "ORDER BY " +
                    "m.month " +
                ")" +
                "SELECT * FROM ebay_listing_count";
                
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_MONTHS + query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int ebayListingCount = resultSet.getInt("ebay_quantity");
                ebayCounts.put(month, ebayListingCount);
            }
        } catch (SQLException e) {
            throw new DataSyncException("Error retrieving ebayListingCount from the database.", "monthylReport", "retrieveEbayListingCount", e);
        }
        
        return ebayCounts;
    }
     
    public TreeMap<String, BigDecimal> calculateTotalEbayListingPrice() throws DataSyncException {
        TreeMap<String, BigDecimal> ebayCounts = new TreeMap<>();
                String query = "ebay_listing_prices AS (" +
                "SELECT " +
                    "m.month, " +
                    "IFNULL(SUM(CASE WHEN mp.marketplace_name = 'Ebay' THEN listing_price ELSE 0 END), 0) AS ebay_listing_price " +
                "FROM " +
                    "months m " +
                "LEFT JOIN " +
                    "listing l ON DATE_FORMAT(l.upload_time, '%Y-%m') = m.month " +
                "LEFT JOIN " +
                    "marketplace mp ON l.marketplace = mp.id " +
                "GROUP BY " +
                    "m.month " +
                "ORDER BY " +
                    "m.month " +
                ")" +
                "SELECT * FROM ebay_listing_prices";
                
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_MONTHS + query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                BigDecimal ebayListingCount = resultSet.getBigDecimal("ebay_listing_price");
                ebayCounts.put(month, ebayListingCount);
            }
        } catch (SQLException e) {
            throw new DataSyncException("Error retrieving ebayTotalListingPrice from the database.", "monthylReport", "retrieveEbayTotalListingPrice", e);
        }
        
        return ebayCounts;
    }
    
    public TreeMap<String, BigDecimal> calculateAverageEbayListingPrice() throws DataSyncException {
        TreeMap<String, BigDecimal> ebayCounts = new TreeMap<>();
                String query = "ebay_listing_averages AS (" +
                "SELECT " +
                    "m.month, " +
                    "IFNULL(SUM(CASE WHEN mp.marketplace_name = 'Ebay' THEN listing_price ELSE 0 END) / " +
                        "NULLIF(COUNT(CASE WHEN mp.marketplace_name = 'Ebay' THEN listing_price END), 0), 0) AS ebay_listing_average " +
                "FROM " +
                    "months m " +
                "LEFT JOIN " +
                    "listing l ON DATE_FORMAT(l.upload_time, '%Y-%m') = m.month " +
                "LEFT JOIN " +
                    "marketplace mp ON l.marketplace = mp.id " +
                "GROUP BY " +
                    "m.month " +
                "ORDER BY " +
                    "m.month " +
                ")" +
                "SELECT * FROM ebay_listing_averages";
                
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_MONTHS + query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                BigDecimal ebayListingCount = resultSet.getBigDecimal("ebay_listing_average").setScale(2, BigDecimal.ROUND_HALF_UP);
                ebayCounts.put(month, ebayListingCount);
            }
        } catch (SQLException e) {
            throw new DataSyncException("Error retrieving ebayAverageListingPrice from the database.", "monthylReport", "retrieveEbayAverageListingPrice", e);
        }
        
        return ebayCounts;
    }
    
    public EbayMonthlyReportDTO generateMonthlyReport() throws DataSyncException {
        EbayMonthlyReportDTO ebayMonthlyReportDTO = new EbayMonthlyReportDTO();
        
        ebayMonthlyReportDTO.setEbayListingCount(calculateTotalEbayListingCount());
        ebayMonthlyReportDTO.setEbayListingPrice(calculateTotalEbayListingPrice());
        ebayMonthlyReportDTO.setEbayAverageListingPrice(calculateAverageEbayListingPrice());
        return ebayMonthlyReportDTO;
    }
    
    
}
