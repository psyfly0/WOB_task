/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author szaboa
 */
public class Listing {
    private UUID id;
    private String title;
    private String description;
    private UUID location_id;
    private BigDecimal listing_price;
    private String currency;
    private Integer quantity;
    private Integer listing_status;
    private Integer marketplace;
    
    @JsonFormat(pattern = "M/d/yyyy")
    private LocalDate upload_time;
    
    private String owner_email_address;
    
    public Listing() {
    }

    public Listing(UUID id, String title, String description, UUID location_id, BigDecimal listing_price, String currency, Integer quantity, Integer listing_status, Integer marketplace, LocalDate upload_time, String owner_email_address) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location_id = location_id;
        this.listing_price = listing_price;
        this.currency = currency;
        this.quantity = quantity;
        this.listing_status = listing_status;
        this.marketplace = marketplace;
        this.upload_time = upload_time;
        this.owner_email_address = owner_email_address;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getLocation_id() {
        return location_id;
    }

    public BigDecimal getListing_price() {
        return listing_price;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getListing_status() {
        return listing_status;
    }

    public Integer getMarketplace() {
        return marketplace;
    }

    public LocalDate getUpload_time() {
        return upload_time;
    }

    public String getOwner_email_address() {
        return owner_email_address;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation_id(UUID location_id) {
        this.location_id = location_id;
    }

    public void setListing_price(BigDecimal listing_price) {
        this.listing_price = listing_price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setListing_status(Integer listing_status) {
        this.listing_status = listing_status;
    }

    public void setMarketplace(Integer marketplace) {
        this.marketplace = marketplace;
    }

    public void setUpload_time(LocalDate upload_time) {
        this.upload_time = upload_time;
    }

    public void setOwner_email_address(String owner_email_address) {
        this.owner_email_address = owner_email_address;
    }
}