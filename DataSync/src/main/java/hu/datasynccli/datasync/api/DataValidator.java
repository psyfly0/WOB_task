/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.api;

import hu.datasynccli.datasync.entity.Listing;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author szaboa
 */
public class DataValidator {
    private Connection connection;

    public DataValidator(Connection connection) {
        this.connection = connection;
    }

    public <T> List<T> validate(List<T> data) throws SQLException {
        List<T> validData = new ArrayList<>();
        List<String> invalidEntries = new ArrayList<>();
        List<String> invalidFields = new ArrayList<>();

        for (T entry : data) {
            if (entry instanceof Listing) {
                Listing listing = (Listing) entry;
                boolean isValid = true;
                
            if (listing.getId() == null) {
                invalidFields.add("id");
                isValid = false;
            }
            if (listing.getTitle() == null) {
                invalidFields.add("title");
                isValid = false;
            }
            
            if (listing.getDescription() == null) {
                invalidFields.add("description");
                isValid = false;
            }
            
            if (listing.getLocation_id() == null) {
                invalidFields.add("location_id");
                isValid = false;
            }
            
            if (!isValidPrice(listing.getListing_price())) {
                invalidFields.add("lsiting_price");
                isValid = false;
            }
            
            if (!isValidCurrency(listing.getCurrency())) {
                invalidFields.add("currency");
                isValid = false;
            }
            
            if (!isValidQuantity(listing.getQuantity())) {
                invalidFields.add("quantity");
                isValid = false;
            }
            
            if (listing.getListing_status() == null) {
                invalidFields.add("listing_status");
                isValid = false;
            }
            
            if (listing.getMarketplace() == null) {
                invalidFields.add("marketplace");
                isValid = false;
            }
            
            if (!isValidEmail(listing.getOwner_email_address())) {
                invalidFields.add("email_address");
                isValid = false;
            }
            
            if (!isValidDate(listing.getUpload_time())) {
                invalidFields.add("upload_time");
                isValid = false;
            }
            
            if (!isValid) {
                String marketplaceName = getMarketplaceName(connection, listing.getMarketplace());
                invalidEntries.add(listing.getId().toString() + ";" + marketplaceName + ";Invalid fields: " + String.join(", ", invalidFields));
            } else {
                validData.add(entry);
            }
            
            invalidFields.clear();
            }
        }

        // Write invalid entries to a CSV file
        writeInvalidEntriesToCSV(invalidEntries);

        return validData;
    }
    
    private static boolean isValidPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        int decimalLength = price.scale();
        return decimalLength == 2;
    }
    
    private static boolean isValidCurrency(String currency) {
        if (currency == null) {
            return false;
        }
        return currency.length() == 3;
    }
    
    private static boolean isValidQuantity(Integer quantity) {
        if (quantity == null) {
            return false;
        }
        return quantity > 0;
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
    
    private static boolean isValidDate(LocalDate date) {
        if (date == null) {
            return false;
        }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        try {
            LocalDate formattedDate = LocalDate.parse(date.format(formatter), formatter);

            return formattedDate.isEqual(date);
        } catch (DateTimeParseException e) {
            return false;
        }    
    }

    private static void writeInvalidEntriesToCSV(List<String> invalidEntries) {
        try (FileWriter writer = new FileWriter("importLog.csv")) {
            for (String entry : invalidEntries) {
                writer.write(entry + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String getMarketplaceName(Connection connection, Integer marketplaceId) throws SQLException {
        String query = "SELECT marketplace_name FROM marketplace WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, marketplaceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("marketplace_name");
            }
        }
        System.out.println("No marketplace found for id: " + marketplaceId);
        return "Unknown Marketplace";
    }
}
