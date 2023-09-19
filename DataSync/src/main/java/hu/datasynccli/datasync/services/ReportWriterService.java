/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.services;

import hu.datasynccli.datasync.dto.AmazonMonthlyReportDTO;
import hu.datasynccli.datasync.dto.EbayMonthlyReportDTO;
import hu.datasynccli.datasync.dto.MonthlyCommonReportDTO;
import hu.datasynccli.datasync.dto.TotalReportDTO;
import hu.datasynccli.datasync.report.ReportWriter;
import java.io.IOException;

/**
 *
 * @author szaboa
 */
public class ReportWriterService {
    private ReportWriter reportWriter;
    private String filePath;
    private TotalReportDTO totalReportDTO;
    private EbayMonthlyReportDTO ebayMonthlyReport;
    private AmazonMonthlyReportDTO amazonMonthlyReport;
    private MonthlyCommonReportDTO monthlyCommonReport;

    public ReportWriterService(
            ReportWriter reportWriter,
            String filePath,
            TotalReportDTO totalReportDTO,
            EbayMonthlyReportDTO ebayMonthlyReport,
            AmazonMonthlyReportDTO amazonMonthlyReport,
            MonthlyCommonReportDTO monthlyCommonReport) {
        this.reportWriter = reportWriter;
        this.filePath = filePath;
        this.totalReportDTO = totalReportDTO;
        this.ebayMonthlyReport = ebayMonthlyReport;
        this.amazonMonthlyReport = amazonMonthlyReport;
        this.monthlyCommonReport = monthlyCommonReport;
    }
    
    public void writeToJSON() throws IOException {
        reportWriter.writeCombinedReportToJSON(filePath, totalReportDTO, ebayMonthlyReport, amazonMonthlyReport, monthlyCommonReport);
    }

}
