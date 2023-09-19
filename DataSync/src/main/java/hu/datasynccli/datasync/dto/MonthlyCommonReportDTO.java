/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.dto;

import java.util.TreeMap;

/**
 *
 * @author szaboa
 */
public class MonthlyCommonReportDTO {
    private TreeMap<String, String> bestLister;

    public TreeMap<String, String> getBestLister() {
        return bestLister;
    }

    public void setBestLister(TreeMap<String, String> bestLister) {
        this.bestLister = bestLister;
    }
    
    
    
}
