/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.services;

import hu.datasynccli.datasync.utils.FTPConnection;
import java.io.IOException;

/**
 *
 * @author szaboa
 */
public class FTPService {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 21;
    private static final String USER = "akos";
    private static final String PASS = "1234";
    
    private FTPConnection ftpConnection;
    private String localFilePath;
    private String fileToUpload;

    public FTPService(FTPConnection ftpConnection, String localFilePath, String fileToUpload) {
        this.ftpConnection = new FTPConnection();
        this.localFilePath = localFilePath;
        this.fileToUpload = fileToUpload;
    }

    public void uploadJSONReportToFTP() {
        try {
            ftpConnection.connect(HOST, PORT, USER, PASS);
            ftpConnection.uploadFile(localFilePath, fileToUpload);
        } catch (IOException e) {
            System.err.println("Error setting up the connection to the FTP server." + e.getMessage());
        } finally {
            try {
                ftpConnection.disconnect();
            } catch (IOException e) {
                System.err.println("Error disconnecting from the FTP server." + e.getMessage());
            }
        }
    }  
}
