/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.controllers;

import hu.datasynccli.datasync.dao.MarketplaceDAO;
import hu.datasynccli.datasync.entity.Marketplace;
import hu.datasynccli.datasync.services.MarketplaceService;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class MarketplaceController {
    private MarketplaceService marketplaceService;
    private MarketplaceDAO marketplaceDAO;

    public MarketplaceController(MarketplaceService marketplaceService, MarketplaceDAO marketplaceDAO) {
        this.marketplaceService = marketplaceService;
        this.marketplaceDAO = marketplaceDAO;
    }
    
    public void syncListings() {
        try {
            List<Marketplace> marketplaces = marketplaceService.fetchListings();
            marketplaceDAO.insertListings(marketplaces);
        } catch (SQLException e) {
            System.err.println("An error occurred while syncing marketplace: " + e.getMessage());
        }
    }
}
