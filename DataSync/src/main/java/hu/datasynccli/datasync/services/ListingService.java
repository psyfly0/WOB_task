/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.services;

import hu.datasynccli.datasync.api.APIService;
import hu.datasynccli.datasync.entity.Listing;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class ListingService {
    private APIService apiService;

    public ListingService(APIService apiService) {
        this.apiService = apiService;
    }

    public List<Listing> fetchListings() {
        return apiService.fetchData("listing", Listing.class);
    }
}
