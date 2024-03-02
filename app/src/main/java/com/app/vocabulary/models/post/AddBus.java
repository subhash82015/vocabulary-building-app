package com.app.vocabulary.models.post;

public class AddBus {

    private Long id;


    private String bus_number;
    private String destination_lan;
    private String destination_lat;
    private String driver_id;
    private String source_lan;
    private String source_lat;
    private String driver_name;

    private String last_lat;
    private String last_lan;

    public AddBus() {
        // Default constructor required for Firestore
    }

    public AddBus(Long id, String bus_number, String destination_lan, String destination_lat, String driver_id, String source_lan, String source_lat, String driver_name, String last_lat, String last_lan) {
        this.id = id;
        this.bus_number = bus_number;
        this.destination_lan = destination_lan;
        this.destination_lat = destination_lat;
        this.driver_id = driver_id;
        this.source_lan = source_lan;
        this.source_lat = source_lat;
        this.driver_name = driver_name;
        this.last_lat = last_lat;
        this.last_lan = last_lan;
        // Assign other properties
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters and setters for each field
    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getDestination_lan() {
        return destination_lan;
    }

    public void setDestination_lan(String destination_lan) {
        this.destination_lan = destination_lan;
    }

    public String getDestination_lat() {
        return destination_lat;
    }

    public void setDestination_lat(String destination_lat) {
        this.destination_lat = destination_lat;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getSource_lan() {
        return source_lan;
    }

    public void setSource_lan(String source_lan) {
        this.source_lan = source_lan;
    }

    public String getSource_lat() {
        return source_lat;
    }

    public void setSource_lat(String source_lat) {
        this.source_lat = source_lat;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }


    public String getLast_lat() {
        return last_lat;
    }

    public void setLast_lat(String last_lat) {
        this.last_lat = last_lat;
    }

    public String getLast_lan() {
        return last_lan;
    }

    public void setLast_lan(String last_lan) {
        this.last_lan = last_lan;
    }

}
