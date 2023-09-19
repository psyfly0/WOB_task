/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.services;

import hu.datasynccli.datasync.api.APIService;
import hu.datasynccli.datasync.entity.Location;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class LocationService {
    private APIService apiService;

    public LocationService(APIService apiService) {
        this.apiService = apiService;
    }

    public List<Location> fetchListings() {
        return apiService.fetchData("location", Location.class);
    }    
}
