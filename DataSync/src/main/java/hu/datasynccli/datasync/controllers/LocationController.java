/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.controllers;

import hu.datasynccli.datasync.dao.LocationDAO;
import hu.datasynccli.datasync.entity.Location;
import hu.datasynccli.datasync.services.LocationService;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class LocationController {
    private LocationService locationService;
    private LocationDAO locationDAO;

    public LocationController(LocationService locationService, LocationDAO locationDAO) {
        this.locationService = locationService;
        this.locationDAO = locationDAO;
    }
    
    public void syncListings() {
        try {
            List<Location> locations = locationService.fetchListings();
            locationDAO.insertListings(locations);
        } catch (SQLException e) {
            System.err.println("An error occurred while syncing locations: " + e.getMessage());
        }
    }
}
