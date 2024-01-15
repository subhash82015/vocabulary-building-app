package com.demo.collegeerp.models.post;

public class AddFees {

    private Long userId;
    private String amount;
    private String remark;
    private String payType;

    public AddFees() {
        // Default constructor required for Firestore
    }

    public AddFees(Long userId, String amount, String remark, String payType) {
        this.userId = userId;
        this.amount = amount;
        this.remark = remark;
        this.payType = payType;

    }

    // Getters and setters for each field
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
