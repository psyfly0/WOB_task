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
public class EbayMonthlyReportDTO {
    private TreeMap<String, Integer> ebayListingCount;
    private TreeMap<String, BigDecimal> ebayListingPrice;
    private TreeMap<String, BigDecimal> ebayAverageListingPrice;
    
    public TreeMap<String, Integer> getEbayListingCount() {
        return ebayListingCount;
    }

    public TreeMap<String, BigDecimal> getEbayListingPrice() {
        return ebayListingPrice;
    }

    public TreeMap<String, BigDecimal> getEbayAverageListingPrice() {
        return ebayAverageListingPrice;
    }
    
 public void setEbayListingCount(TreeMap<String, Integer> ebayListingCount) {
        this.ebayListingCount = ebayListingCount;
    }

    public void setEbayListingPrice(TreeMap<String, BigDecimal> ebayListingPrice) {
        this.ebayListingPrice = ebayListingPrice;
    }

    public void setEbayAverageListingPrice(TreeMap<String, BigDecimal> ebayAverageListingPrice) {
        this.ebayAverageListingPrice = ebayAverageListingPrice;
    }
}
