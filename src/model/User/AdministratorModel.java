package model.User;

import model.PaymentGateway.Wallet;
import model.Hostel.Hostel;
import java.util.ArrayList;
import java.util.List;

public class AdministratorModel extends UserModel {
    private List<Hostel> hostels= new ArrayList<Hostel>();
    public void appendHostel(Hostel hostel) {
        hostels.add(hostel);
    }

    public List<Hostel> getHostels() {
        return new ArrayList<>(hostels);
    }


    public AdministratorModel(String userId, String name, String email, String phoneNumber, String hostelId) {
        super(userId, name, email, phoneNumber);
    }
    
    // Administrator-specific functionalities
    public void addFundsToStudentWallet(Wallet wallet, double amount) {
        wallet.addFunds(amount, "administrator");
    }

    @Override
    public List<Hostel> getHostel() {
        // TODO Auto-generated method stub
        return null;
    }
}
