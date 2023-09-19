/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.controllers;

import hu.datasynccli.datasync.dao.ListingStatusDAO;
import hu.datasynccli.datasync.entity.ListingStatus;
import hu.datasynccli.datasync.services.ListingStatusService;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class ListingStatusController {
    private ListingStatusService listingStatusService;
    private ListingStatusDAO listingStatusDAO;

    public ListingStatusController(ListingStatusService listingStatusService, ListingStatusDAO listingStatusDAO) {
        this.listingStatusService = listingStatusService;
        this.listingStatusDAO = listingStatusDAO;
    }
    
    public void syncListings() {
        try {
            List<ListingStatus> listingStatuses = listingStatusService.fetchListings();
            listingStatusDAO.insertListings(listingStatuses);
        } catch (SQLException e) {
            System.err.println("An error occurred while syncing listing_status: " + e.getMessage());
        }
    }
}
