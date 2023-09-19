/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.controllers;

import hu.datasynccli.datasync.services.ReportWriterService;
import java.io.IOException;

/**
 *
 * @author szaboa
 */
public class ReportWriterController {
    private ReportWriterService reportWriterService;

    public ReportWriterController(ReportWriterService reportWriterService) {
        this.reportWriterService = reportWriterService;
    }
    
    public void writeReportToJSON() throws IOException {
        reportWriterService.writeToJSON();
    }
}
