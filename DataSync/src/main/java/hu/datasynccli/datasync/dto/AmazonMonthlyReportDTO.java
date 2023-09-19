/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.dto;

import java.math.BigDecimal;
import java.util.TreeMap;

/**
 *
 * @author szaboa
 */
public class AmazonMonthlyReportDTO {
    private TreeMap<String, Integer> amazonListingCount;
    private TreeMap<String, BigDecimal> amazonListingPrice;
    private TreeMap<String, BigDecimal> amazonAverageListingPrice;  
    
    public TreeMap<String, Integer> getAmazonListingCount() {
        return amazonListingCount;
    }

    public TreeMap<String, BigDecimal> getAmazonListingPrice() {
        return amazonListingPrice;
    }

    public TreeMap<String, BigDecimal> getAmazonAverageListingPrice() {
        return amazonAverageListingPrice;
    }
    
    public void setAmazonListingCount(TreeMap<String, Integer> amazonListingCount) {
        this.amazonListingCount = amazonListingCount;
    }

    public void setAmazonListingPrice(TreeMap<String, BigDecimal> amazonListingPrice) {
        this.amazonListingPrice = amazonListingPrice;
    }

    public void setAmazonAverageListingPrice(TreeMap<String, BigDecimal> amazonAverageListingPrice) {
        this.amazonAverageListingPrice = amazonAverageListingPrice;
    }
}
