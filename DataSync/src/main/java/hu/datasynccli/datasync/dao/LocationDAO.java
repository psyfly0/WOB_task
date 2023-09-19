/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.dao;

import hu.datasynccli.datasync.entity.Location;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class LocationDAO {
    private Connection connection;

    public LocationDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insertListings(List<Location> locations) throws DataSyncException {
        String query = "INSERT INTO location (id, manager_name, phone, address_primary, address_secondary, country, town, postal_code) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

         try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Location location : locations) {
                preparedStatement.setObject(1, location.getId().toString());
                preparedStatement.setString(2, location.getManager_name());
                preparedStatement.setString(3, location.getPhone());
                preparedStatement.setString(4, location.getAddress_primary());
                preparedStatement.setString(5, location.getAddress_secondary());
                preparedStatement.setString(6, location.getCountry());
                preparedStatement.setString(7, location.getTown());
                preparedStatement.setString(8, location.getPostal_code());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
         } catch (SQLException e) {
             throw new DataSyncException("Error inserting locations into the database.", "location", "inserLocations", e);
         }
    }
    
    
}
