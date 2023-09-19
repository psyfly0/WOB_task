/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.dto;

import java.math.BigDecimal;

/**
 *
 * @author szaboa
 */
public class TotalReportDTO {
    private int totalListingCount;
    private int totalEbayListingCount;
    private BigDecimal totalEbayListingPrice;
    private BigDecimal averageEbayListingPrice;
    private int totalAmazonListingCount;
    private BigDecimal totalAmazonListingPrice;
    private BigDecimal averageAmazonListingPrice;
    private String bestLister;

    public int getTotalListingCount() {
        return totalListingCount;
    }
    
    public int getTotalEbayListingCount() {
        return totalEbayListingCount;
    }

    public BigDecimal getTotalEbayListingPrice() {
        return totalEbayListingPrice;
    }

    public BigDecimal getAverageEbayListingPrice() {
        return averageEbayListingPrice;
    }

    public int getTotalAmazonListingCount() {
        return totalAmazonListingCount;
    }

    public BigDecimal getTotalAmazonListingPrice() {
        return totalAmazonListingPrice;
    }

    public BigDecimal getAverageAmazonListingPrice() {
        return averageAmazonListingPrice;
    }

    public String getBestLister() {
        return bestLister;
    }

    public void setTotalListingCount(int totalListingCount) {
        this.totalListingCount = totalListingCount;
    }
    
    public void setTotalEbayListingCount(int totalEbayListingCount) {
        this.totalEbayListingCount = totalEbayListingCount;
    }

    public void setTotalEbayListingPrice(BigDecimal totalEbayListingPrice) {
        this.totalEbayListingPrice = totalEbayListingPrice;
    }

    public void setAverageEbayListingPrice(BigDecimal averageEbayListingPrice) {
        this.averageEbayListingPrice = averageEbayListingPrice;
    }

    public void setTotalAmazonListingCount(int totalAmazonListingCount) {
        this.totalAmazonListingCount = totalAmazonListingCount;
    }

    public void setTotalAmazonListingPrice(BigDecimal totalAmazonListingPrice) {
        this.totalAmazonListingPrice = totalAmazonListingPrice;
    }

    public void setAverageAmazonListingPrice(BigDecimal averageAmazonListingPrice) {
        this.averageAmazonListingPrice = averageAmazonListingPrice;
    }

    public void setBestLister(String bestLister) {
        this.bestLister = bestLister;
    }
    
    
}
