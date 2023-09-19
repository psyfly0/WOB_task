/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.services;

import hu.datasynccli.datasync.api.APIService;
import hu.datasynccli.datasync.entity.ListingStatus;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class ListingStatusService {
    private APIService apiService;

    public ListingStatusService(APIService apiService) {
        this.apiService = apiService;
    }

    public List<ListingStatus> fetchListings() {
        return apiService.fetchData("listingStatus", ListingStatus.class);
    }      
}
