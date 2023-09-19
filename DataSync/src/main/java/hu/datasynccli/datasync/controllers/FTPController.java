/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.controllers;

import hu.datasynccli.datasync.services.FTPService;

/**
 *
 * @author szaboa
 */
public class FTPController {
    private FTPService ftpService;

    public FTPController(FTPService ftpService) {
        this.ftpService = ftpService;
    }
    
    public void uploadJSONReport() {
        ftpService.uploadJSONReportToFTP();
    }
}
