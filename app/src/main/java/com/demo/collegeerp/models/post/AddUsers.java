package com.demo.collegeerp.models.post;

public class AddUsers {

    private String branch;
    private String course;
    private String full_name;
    private String mobile;
    private String password;
    private String roll_no;
    private String section;
    private Long userid;
    // Add other properties as needed
    private String usertype;
    private String fees_amount;

    public AddUsers() {
        // Default constructor required for Firestore
    }

    public AddUsers(String branch, String course, String full_name, String mobile, String password, String roll_no, String section, Long userid, String usertype, String fees_amount) {
        this.branch = branch;
        this.course = course;
        this.full_name = full_name;
        this.mobile = mobile;
        this.password = password;
        this.roll_no = roll_no;
        this.section = section;
        this.userid = userid;
        this.usertype = usertype;
        this.fees_amount = fees_amount;
        // Assign other properties
    }

    // Getters and setters for each field
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getFees_amount() {
        return fees_amount;
    }

    public void setFees_amount(String fees_amount) {
        this.fees_amount = fees_amount;
    }
}
