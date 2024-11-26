package model.User;

import java.util.List;

import model.Hostel.Hostel;

public class MaintenanceStaffModel extends UserModel {
    private String address;

    public MaintenanceStaffModel(String userId, String name, String email, String phoneNumber, String address) {
        super(userId, name, email, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public List<Hostel> getHostels() {
        // TODO Auto-generated method stub
        return null;
    }
}
