/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.controllers;

import hu.datasynccli.datasync.dao.ListingDAO;
import hu.datasynccli.datasync.entity.Listing;
import hu.datasynccli.datasync.services.ListingService;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class ListingController {
    private ListingService listingService;
    private ListingDAO listingDAO;

    public ListingController(ListingService listingService, ListingDAO listingDAO) {
        this.listingService = listingService;
        this.listingDAO = listingDAO;
    }

    public void syncListings() throws SQLException {
      
            List<Listing> listings = listingService.fetchListings();
            listingDAO.insertListings(listings);
   
    }
}
