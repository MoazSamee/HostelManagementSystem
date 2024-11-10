package model.PaymentGateway;

import model.User.StudentModel;
import model.Hostel.Hostel;

public class PendingPayment {
    private String userId;
    private String idType;
    private double amount;
    private String reason;

    public PendingPayment(String userId, String idType, double amount, String reason) {
        this.userId = userId;
        this.idType = idType;
        this.amount = amount;
        this.reason = reason;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
