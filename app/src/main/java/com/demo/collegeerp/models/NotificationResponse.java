package com.demo.collegeerp.models;

public class NotificationResponse {
    private Long id;
    private String access_type;
    private String body;
    private String title;
    private String dt;


    public NotificationResponse() {
        // Required empty constructor for Firestore
    }

    public NotificationResponse(Long id, String access_type, String body, String title, String dt) {
        this.id = id;
        this.access_type = access_type;
        this.body = body;
        this.title = title;
        this.dt = dt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessType() {
        return access_type;
    }

    public void setAccessType(String access_type) {
        this.access_type = access_type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}