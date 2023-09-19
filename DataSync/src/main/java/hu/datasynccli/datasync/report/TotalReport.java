/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.report;

import hu.datasynccli.datasync.dto.MonthlyCommonReportDTO;
import hu.datasynccli.datasync.dto.TotalReportDTO;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author szaboa
 */
public class TotalReport {
    private Connection connection;
    private MonthlyReportEbay monthlyReportEbay;
    private MonthlyReportAmazon monthlyReportAmazon;

    public TotalReport(Connection connection, MonthlyReportEbay monthyReportEbay, MonthlyReportAmazon monthlyReportAmazon) {
        this.connection = connection;
        this.monthlyReportEbay = monthyReportEbay;
        this.monthlyReportAmazon = monthlyReportAmazon;
    }
    
    public int calculateTotalListingCount() throws DataSyncException {   
        
        return calculateTotalEbayCount() + calculateTotalAmazonCount();
    }
    
    public int calculateTotalEbayCount() throws DataSyncException {
        Map<String, Integer> ebayCounts = monthlyReportEbay.calculateTotalEbayListingCount();

        int totalEbayCount = 0;
        
        for (Map.Entry<String, Integer> entry : ebayCounts.entrySet()) {
            totalEbayCount += entry.getValue();
        }
        
        return totalEbayCount;
    }
    
    public BigDecimal calculateTotalEbayListingPrice() throws DataSyncException {
        Map<String, BigDecimal> ebayCounts = monthlyReportEbay.calculateTotalEbayListingPrice();
        
        BigDecimal totalEbayListingPrice = BigDecimal.ZERO;
        
        for (Map.Entry<String, BigDecimal> entry : ebayCounts.entrySet()) {
            totalEbayListingPrice = totalEbayListingPrice.add(entry.getValue());
        }
        
        return totalEbayListingPrice;
    }
    
    public BigDecimal calculateAverageEbayListingPrice() throws DataSyncException {
        int totalEbayCount = calculateTotalEbayCount();
        if (totalEbayCount == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalEbayListingPrice = calculateTotalEbayListingPrice();
        return totalEbayListingPrice.divide(BigDecimal.valueOf(totalEbayCount), RoundingMode.HALF_UP);
    }
    
    public int calculateTotalAmazonCount() throws DataSyncException {
        Map<String, Integer> amazonCounts = monthlyReportAmazon.calculateTotalAmazonListingCount();

        int totalAmazonCount = 0;
        
        for (Map.Entry<String, Integer> entry : amazonCounts.entrySet()) {
            totalAmazonCount += entry.getValue();
        }
        
        return totalAmazonCount;
    }
    
    public BigDecimal calculateTotalAmazonListingPrice() throws DataSyncException {
        Map<String, BigDecimal> amazonCounts = monthlyReportAmazon.calculateTotalAmazonListingPrice();
        
        BigDecimal totalAmazonListingPrice = BigDecimal.ZERO;
        
        for (Map.Entry<String, BigDecimal> entry : amazonCounts.entrySet()) {
            totalAmazonListingPrice = totalAmazonListingPrice.add(entry.getValue());
        }
        
        return totalAmazonListingPrice;
    }
    
    public BigDecimal calculateAverageAmazonListingPrice() throws DataSyncException {
        int totalAmazonCount = calculateTotalAmazonCount();
        if (totalAmazonCount == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalAmazonListingPrice = calculateTotalAmazonListingPrice();
        return totalAmazonListingPrice.divide(BigDecimal.valueOf(totalAmazonCount), RoundingMode.HALF_UP);
    }
    
    public String calculateBestLister() throws DataSyncException {
        Map<String, String> bestListerPerMonth = calculateBestListerPerMonth();

        Map<String, Integer> listerOccurrences = new HashMap<>();
        for (String lister : bestListerPerMonth.values()) {
            listerOccurrences.put(lister, listerOccurrences.getOrDefault(lister, 0) + 1);
        }

        String bestLister = null;
        int maxOccurrences = 0;
        for (Map.Entry<String, Integer> entry : listerOccurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                bestLister = entry.getKey();
                maxOccurrences = entry.getValue();
            }
        }

        return bestLister;
    }
    
    // MONTHLY REPORT!!
    public TreeMap<String, String> calculateBestListerPerMonth() throws DataSyncException {
        TreeMap<String, String> bestLister = new TreeMap<>();
        String query = "WITH months AS (" +
            "SELECT DATE_FORMAT(upload_time, '%Y-%m') AS month " +
            "FROM listing " +
            "GROUP BY month " +
            "), " +
            "listing_quantities AS (" +
            "SELECT " +
                "m.month, " +
                "l.owner_email_address, " +
                "SUM(quantity) AS total_quantity " +
            "FROM " +
                "months m " +
            "LEFT JOIN " +
                "listing l ON DATE_FORMAT(l.upload_time, '%Y-%m') = m.month " +
            "GROUP BY " +
                "m.month, l.owner_email_address " +
            ")," +
            "max_quantities_per_month AS (" +
                "SELECT " +
                    "month, " +
                    "MAX(total_quantity) AS max_quantity " +
                "FROM " +
                    "listing_quantities " +
                "GROUP BY " +
                    "month" +
            ")" +
            "SELECT " +
                "mqpm.month, " +
                "lq.owner_email_address AS best_lister " +
            "FROM " +
                "max_quantities_per_month mqpm " +
            "JOIN " +
                "listing_quantities lq " +
            "ON " +
                "mqpm.month = lq.month AND mqpm.max_quantity = lq.total_quantity " +
            "ORDER BY " +
                "mqpm.month ;";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                String lister = resultSet.getString("best_lister");
                bestLister.put(month, lister);
            }
        } catch (SQLException e) {
            throw new DataSyncException("Error retrieving bestLister from the database.", "monthylReport", "retrieveBestLister", e);
        }
        return bestLister;
    }
    
    public MonthlyCommonReportDTO generateMonthlyReport() throws DataSyncException {
        MonthlyCommonReportDTO monthlyReportDTO = new MonthlyCommonReportDTO();
        
        monthlyReportDTO.setBestLister(calculateBestListerPerMonth());
        return monthlyReportDTO;
    }
    
    public TotalReportDTO generateTotalReport() throws DataSyncException {
        TotalReportDTO totalReportDTO = new TotalReportDTO();
        
        totalReportDTO.setTotalListingCount(calculateTotalListingCount());
        totalReportDTO.setTotalEbayListingCount(calculateTotalEbayCount());
        totalReportDTO.setTotalEbayListingPrice(calculateTotalEbayListingPrice());
        totalReportDTO.setAverageEbayListingPrice(calculateAverageEbayListingPrice());
        totalReportDTO.setTotalAmazonListingCount(calculateTotalAmazonCount());
        totalReportDTO.setTotalAmazonListingPrice(calculateTotalAmazonListingPrice());
        totalReportDTO.setAverageAmazonListingPrice(calculateAverageAmazonListingPrice());
        totalReportDTO.setBestLister(calculateBestLister());
        return totalReportDTO;
    }
    
}
