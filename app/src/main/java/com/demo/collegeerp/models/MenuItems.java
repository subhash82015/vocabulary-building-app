package com.demo.collegeerp.models;

public class MenuItems {
    private int id;
    private String name;
    private int icon;
    private boolean status;

    public MenuItems(int id, String name, int icon, boolean status) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}