package model.PaymentGateway;

import java.util.LinkedList;
import java.util.Queue;

public class Wallet {
    private String userId;
    private double currentBalance;
    private Queue<String> paymentQueue;

    public Wallet(String userId, double initialBalance) {
        this.userId = userId;
        this.currentBalance = initialBalance;
        this.paymentQueue = new LinkedList<>();
    }

    public void addFunds(double amount, String method) {
        if (method.equals("online") || method.equals("administrator")) {
            currentBalance += amount;
        }
    }

    public void deductForMaintenance(Wallet hostelWallet, double salary) {
        if (hostelWallet.currentBalance >= salary) {
            hostelWallet.currentBalance -= salary;
            currentBalance += salary;
        } else {
            paymentQueue.add("Insufficient funds. Invoice added to queue.");
        }
    }

    public void chargeMonthlyRent(double rentAmount, Wallet studentWallet) {
        if (studentWallet.currentBalance >= rentAmount) {
            studentWallet.currentBalance -= rentAmount;
            currentBalance += rentAmount;
        } else {
            paymentQueue.add("Pending rent payment from student ID: " + studentWallet.userId);
        }
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public String getUserId() {
        return userId;
    }
}
