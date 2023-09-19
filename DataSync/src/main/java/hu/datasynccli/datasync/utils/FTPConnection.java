/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
/**
 *
 * @author szaboa
 */
public class FTPConnection {
    private FTPClient ftpClient;

    public FTPConnection() {
        ftpClient = new FTPClient();
    }

    public void connect(String server, int port, String user, String pass) throws IOException {
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    public void uploadFile(String localFilePath, String remoteFilePath) throws IOException {
        File localFile = new File(localFilePath);
        try (FileInputStream inputStream = new FileInputStream(localFile)) {
            boolean success = ftpClient.storeFile(remoteFilePath, inputStream);
            if (!success) {
                throw new IOException("Failed to upload file to FTP server");
            }
        }
    }

    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
