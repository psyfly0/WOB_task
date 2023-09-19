/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.services;

import hu.datasynccli.datasync.api.APIService;
import hu.datasynccli.datasync.entity.Marketplace;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class MarketplaceService {
    private APIService apiService;

    public MarketplaceService(APIService apiService) {
        this.apiService = apiService;
    }

    public List<Marketplace> fetchListings() {
        return apiService.fetchData("marketplace", Marketplace.class);
    }    
}
