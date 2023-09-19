/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.dao;

import hu.datasynccli.datasync.entity.ListingStatus;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class ListingStatusDAO {
    private Connection connection;

    public ListingStatusDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insertListings(List<ListingStatus> listingStatuses) throws DataSyncException {
        String query = "INSERT INTO listingStatus (id, status_name) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (ListingStatus listingStatus : listingStatuses) {
                preparedStatement.setInt(1, listingStatus.getId());
                preparedStatement.setString(2, listingStatus.getStatus_name());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DataSyncException("Error inserting listingStatuses into the database.", "listingStatuses", "insertListingStatuses", e);
        }
    }
}
