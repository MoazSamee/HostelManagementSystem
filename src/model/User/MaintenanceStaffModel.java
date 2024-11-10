package model.User;

public class MaintenanceStaffModel extends UserModel {
    private String address;

    public MaintenanceStaffModel(String userId, String name, String email, String phoneNumber, String hostelId, String address) {
        super(userId, name, email, phoneNumber, hostelId);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
