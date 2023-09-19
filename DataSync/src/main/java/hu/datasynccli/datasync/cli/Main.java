/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.cli;

import hu.datasynccli.datasync.api.APIService;
import hu.datasynccli.datasync.controllers.FTPController;
import hu.datasynccli.datasync.controllers.ListingController;
import hu.datasynccli.datasync.controllers.ListingStatusController;
import hu.datasynccli.datasync.controllers.LocationController;
import hu.datasynccli.datasync.controllers.MarketplaceController;
import hu.datasynccli.datasync.controllers.ReportWriterController;
import hu.datasynccli.datasync.services.ListingService;
import hu.datasynccli.datasync.dao.ListingDAO;
import hu.datasynccli.datasync.dao.ListingStatusDAO;
import hu.datasynccli.datasync.dao.LocationDAO;
import hu.datasynccli.datasync.dao.MarketplaceDAO;
import hu.datasynccli.datasync.dto.AmazonMonthlyReportDTO;
import hu.datasynccli.datasync.dto.EbayMonthlyReportDTO;
import hu.datasynccli.datasync.dto.MonthlyCommonReportDTO;
import hu.datasynccli.datasync.dto.TotalReportDTO;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import hu.datasynccli.datasync.report.MonthlyReportAmazon;
import hu.datasynccli.datasync.report.MonthlyReportEbay;
import hu.datasynccli.datasync.report.ReportWriter;
import hu.datasynccli.datasync.report.TotalReport;
import hu.datasynccli.datasync.services.FTPService;
import hu.datasynccli.datasync.services.ListingStatusService;
import hu.datasynccli.datasync.services.LocationService;
import hu.datasynccli.datasync.services.MarketplaceService;
import hu.datasynccli.datasync.services.ReportWriterService;
import hu.datasynccli.datasync.utils.DatabaseConnection;
import hu.datasynccli.datasync.utils.FTPConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author szaboa
 */
public class Main {
    private static final String API_KEY = "63304c70";
    private static final String REPORT_FILE = "report.json";
    private static final String FTP_LOCAL_PATH = "d:\\ftp\\";
    
    public static void main(String[] args) {
        // Establish connection
        try (Connection connection = DatabaseConnection.getConnection()) {

            // API
            APIService apiService = new APIService(API_KEY);

            // Services
            MarketplaceService marketplaceService = new MarketplaceService(apiService);
            ListingStatusService listingStatusService = new ListingStatusService(apiService);
            LocationService locationService = new LocationService(apiService);
            ListingService listingService = new ListingService(apiService);

            // DAOs
            MarketplaceDAO marketplaceDAO = new MarketplaceDAO(connection);
            ListingStatusDAO listingStatusDAO = new ListingStatusDAO(connection);
            LocationDAO locationDAO = new LocationDAO(connection);
            ListingDAO listingDAO = new ListingDAO(connection);

            // Controllers
            MarketplaceController marketplaceController = new MarketplaceController(marketplaceService, marketplaceDAO);
            ListingStatusController listingStatusController = new ListingStatusController(listingStatusService, listingStatusDAO);
            LocationController locationController = new LocationController(locationService, locationDAO);
            ListingController listingController = new ListingController(listingService, listingDAO);

            // Syncing lists
            marketplaceController.syncListings();
            listingStatusController.syncListings();
            locationController.syncListings();
            listingController.syncListings();
            
            // Reports
            MonthlyReportEbay monthlyReportEbay = new MonthlyReportEbay(connection);
            MonthlyReportAmazon monthlyReportAmazon = new MonthlyReportAmazon(connection);
            TotalReport totalReport = new TotalReport(connection, monthlyReportEbay, monthlyReportAmazon);
            
            EbayMonthlyReportDTO ebayMonthlyReport = monthlyReportEbay.generateMonthlyReport();
            AmazonMonthlyReportDTO amazonMonthlyReport = monthlyReportAmazon.generateMonthlyReport();
            MonthlyCommonReportDTO monthlyCommonReport = totalReport.generateMonthlyReport();
            TotalReportDTO totalReportDTO = totalReport.generateTotalReport();
                      
            ReportWriter reportWriter = new ReportWriter();
            ReportWriterService reportWriterService = new ReportWriterService(reportWriter, REPORT_FILE, totalReportDTO, ebayMonthlyReport, amazonMonthlyReport, monthlyCommonReport);
            ReportWriterController reportWriterController = new ReportWriterController(reportWriterService);
            
            reportWriterController.writeReportToJSON();
            
            // FTP
            FTPConnection ftpConnection = new FTPConnection();
            FTPService ftpService = new FTPService(ftpConnection, FTP_LOCAL_PATH, REPORT_FILE);
            FTPController ftpController = new FTPController(ftpService);
            
            ftpController.uploadJSONReport();
        
        } catch (DataSyncException customException) {
            System.err.println("An error occurred in " + customException.getOperationName() +
            " for table " + customException.getTableName() + ".");
        } catch (SQLException | IOException e) {
            System.err.println("A database error occurred: " + e.getMessage());
        }
    }
}
