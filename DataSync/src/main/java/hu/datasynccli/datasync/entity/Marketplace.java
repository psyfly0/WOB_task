/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.datasynccli.datasync.entity;

/**
 *
 * @author szaboa
 */
public class Marketplace {
    private Integer id;
    private String marketplace_name;

    public Marketplace() {
    }

    public Marketplace(Integer id, String marketplace_name) {
        this.id = id;
        this.marketplace_name = marketplace_name;
    }

    public int getId() {
        return id;
    }

    public String getMarketplace_name() {
        return marketplace_name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMarketplace_name(String marketplace_name) {
        this.marketplace_name = marketplace_name;
    }
    
    
}
