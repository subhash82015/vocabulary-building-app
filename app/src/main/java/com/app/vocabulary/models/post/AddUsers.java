package com.app.vocabulary.models.post;

public class AddUsers {
    private String full_name;
    private String mobile;
    private String password;
    private Long userid;
    // Add other properties as needed
    private Long type;
    private String email;

    public AddUsers() {
        // Default constructor required for Firestore
    }

    public AddUsers(Long userid, String full_name, String mobile, String password, String email, Long type) {
        this.userid = userid;
        this.email = email;
        this.full_name = full_name;
        this.mobile = mobile;
        this.password = password;
        this.type = type;
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getUsertype() {
        return type;
    }

    public void setUsertype(Long type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
