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
public class ListingStatus {
    private Integer id;
    private String status_name;

    public ListingStatus() {
    }

    public ListingStatus(Integer id, String status_name) {
        this.id = id;
        this.status_name = status_name;
    }

    public int getId() {
        return id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
    
}
