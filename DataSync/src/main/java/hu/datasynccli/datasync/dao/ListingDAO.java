/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.dao;

import hu.datasynccli.datasync.api.DataValidator;
import hu.datasynccli.datasync.entity.Listing;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class ListingDAO {
    private Connection connection;
    private DataValidator dataValidator;

    public ListingDAO(Connection connection) {
        this.connection = connection;
        this.dataValidator = new DataValidator(connection);
    }  
    
    public void insertListings(List<Listing> listings) throws DataSyncException {        
        String query = "INSERT INTO listing (id, title, description, location_id, listing_price, currency, quantity, listing_status, marketplace, upload_time, owner_email_address) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            List<Listing> validListings = dataValidator.validate(listings);
            
            for (Listing listing : validListings) {
                preparedStatement.setString(1, listing.getId().toString());
                preparedStatement.setString(2, listing.getTitle());
                preparedStatement.setString(3, listing.getDescription());
                preparedStatement.setString(4, listing.getLocation_id().toString());
                preparedStatement.setBigDecimal(5, listing.getListing_price());
                preparedStatement.setString(6, listing.getCurrency());
                preparedStatement.setInt(7, listing.getQuantity());
                preparedStatement.setInt(8, listing.getListing_status());
                preparedStatement.setInt(9, listing.getMarketplace());
                preparedStatement.setDate(10, Date.valueOf(listing.getUpload_time()));
                preparedStatement.setString(11, listing.getOwner_email_address());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DataSyncException("Error inserting listings into the database.", "listing", "insertListings", e);
        }
    }
}
