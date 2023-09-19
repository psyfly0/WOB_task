/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.dao;

import hu.datasynccli.datasync.entity.Marketplace;
import hu.datasynccli.datasync.exceptions.DataSyncException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author szaboa
 */
public class MarketplaceDAO {
    private Connection connection;

    public MarketplaceDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insertListings(List<Marketplace> marketplaces) throws DataSyncException {
        String query = "INSERT INTO marketplace (id, marketplace_name) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Marketplace marketplace : marketplaces) {
                preparedStatement.setInt(1, marketplace.getId());
                preparedStatement.setString(2, marketplace.getMarketplace_name());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DataSyncException("Error inserting marketplaces into the database.", "marketplace", "insertMarketplaces", e);
        }
    }
}
