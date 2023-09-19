/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.report;

import hu.datasynccli.datasync.dto.AmazonMonthlyReportDTO;
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
public class MonthlyReportAmazon {
    private static final String SQL_MONTHS = "WITH months AS (" +
            "SELECT DATE_FORMAT(upload_time, '%Y-%m') AS month " +
            "FROM listing " +
            "GROUP BY month" +
            "),";
    
    private Connection connection;

    public MonthlyReportAmazon(Connection connection) {
        this.connection = connection;
    }
    
    public TreeMap<String, Integer> calculateTotalAmazonListingCount() throws DataSyncException {
        TreeMap<String, Integer> amazonCounts = new TreeMap<>();
                String query = "amazon_listing_count AS (" +
                "SELECT " +
                    "m.month, " +
                    "IFNULL(SUM(CASE WHEN mp.marketplace_name = 'Amazon' THEN quantity ELSE 0 END), 0) AS amazon_quantity " +
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
                "SELECT * FROM amazon_listing_count";
                
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_MONTHS + query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int amazonListingCount = resultSet.getInt("amazon_quantity");
                amazonCounts.put(month, amazonListingCount);
            }
        } catch (SQLException e) {
            throw new DataSyncException("Error retrieving amazonListingCount from the database.", "monthylReport", "retrieveAmazonListingCount", e);
        }
        
        return amazonCounts;
    }
    
    public TreeMap<String, BigDecimal> calculateTotalAmazonListingPrice() throws DataSyncException {
        TreeMap<String, BigDecimal> amazonCounts = new TreeMap<>();
                String query = "amazon_listing_prices AS (" +
                "SELECT " +
                    "m.month, " +
                    "IFNULL(SUM(CASE WHEN mp.marketplace_name = 'Amazon' THEN listing_price ELSE 0 END), 0) AS amazon_listing_price " +
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
                "SELECT * FROM amazon_listing_prices";
                
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_MONTHS + query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                BigDecimal amazonListingCount = resultSet.getBigDecimal("amazon_listing_price");
                amazonCounts.put(month, amazonListingCount);
            }
        } catch (SQLException e) {
            throw new DataSyncException("Error retrieving amazonListingCount from the database.", "monthylReport", "retrieveAmazonTotalListingPrice", e);
        }
        
        return amazonCounts;
    }
    
    public TreeMap<String, BigDecimal> calculateAverageAmazonListingPrice() throws DataSyncException {
        TreeMap<String, BigDecimal> amazonCounts = new TreeMap<>();
                String query = "amazon_listing_averages AS (" +
                "SELECT " +
                    "m.month, " +
                    "IFNULL(SUM(CASE WHEN mp.marketplace_name = 'Amazon' THEN listing_price ELSE 0 END) / " +
                        "NULLIF(COUNT(CASE WHEN mp.marketplace_name = 'Amazon' THEN listing_price END), 0), 0) AS amazon_listing_average " +
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
                "SELECT * FROM amazon_listing_averages";
                
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_MONTHS + query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                BigDecimal amazonListingCount = resultSet.getBigDecimal("amazon_listing_average").setScale(2, BigDecimal.ROUND_HALF_UP);
                amazonCounts.put(month, amazonListingCount);
            }
        } catch (SQLException e) {
            throw new DataSyncException("Error retrieving amazonListingCount from the database.", "monthylReport", "retrieveAmazonAverageListingPrice", e);
        }
        
        return amazonCounts;
    }
    
    public AmazonMonthlyReportDTO generateMonthlyReport() throws DataSyncException {
        AmazonMonthlyReportDTO amazonMonthlyReportDTO = new AmazonMonthlyReportDTO();
        
        amazonMonthlyReportDTO.setAmazonListingCount(calculateTotalAmazonListingCount());
        amazonMonthlyReportDTO.setAmazonListingPrice(calculateTotalAmazonListingPrice());
        amazonMonthlyReportDTO.setAmazonAverageListingPrice(calculateAverageAmazonListingPrice());
        return amazonMonthlyReportDTO;
    }
}
