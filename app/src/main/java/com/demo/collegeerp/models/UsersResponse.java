package com.demo.collegeerp.models;

public class UsersResponse {
    private Long userid;
    private String branch;
    private String section;
    private String roll_no;
    private String password;
    private String mobile;
    private String full_name;
    private String course;
    private String usertype;


    public UsersResponse() {
        // Required empty constructor for Firestore
    }

    public UsersResponse(Long userid, String branch, String section, String roll_no, String password, String mobile, String full_name, String course, String usertype) {
        this.userid = userid;
        this.branch = branch;
        this.section = section;
        this.roll_no = roll_no;
        this.password = password;
        this.mobile = mobile;
        this.full_name = full_name;
        this.course = course;
        this.usertype = usertype;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}