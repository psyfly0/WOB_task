/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.datasynccli.datasync.dto.AmazonMonthlyReportDTO;
import hu.datasynccli.datasync.dto.EbayMonthlyReportDTO;
import hu.datasynccli.datasync.dto.MonthlyCommonReportDTO;
import hu.datasynccli.datasync.dto.TotalReportDTO;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 *
 * @author szaboa
 */
public class ReportWriter {
    private final ObjectMapper objectMapper;

    public ReportWriter() {
        this.objectMapper = new ObjectMapper();
    }
    
    public void writeCombinedReportToJSON(String filePath, TotalReportDTO totalReportDTO, EbayMonthlyReportDTO ebayMonthlyReport, AmazonMonthlyReportDTO amazonMonthlyReport, MonthlyCommonReportDTO monthlyCommonReport) throws IOException {
        TreeMap<String, Object> combinedReport = new TreeMap<>();
        combinedReport.put("Total_Report", totalReportDTO);
        combinedReport.put("Ebay_Monthly_Report", ebayMonthlyReport);
        combinedReport.put("Amazon_Monthy_Report", amazonMonthlyReport);
        combinedReport.put("Common_Monthly_Report", monthlyCommonReport);
        
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), combinedReport);
    }

}
