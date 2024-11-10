package model.User;


public class AdministratorModel extends UserModel {

    public AdministratorModel(String userId, String name, String email, String phoneNumber, String hostelId) {
        super(userId, name, email, phoneNumber, hostelId);
    }
    
    // Administrator-specific functionalities
    public void addFundsToStudentWallet(Wallet wallet, double amount) {
        wallet.addFunds(amount, "administrator");
    }
}
