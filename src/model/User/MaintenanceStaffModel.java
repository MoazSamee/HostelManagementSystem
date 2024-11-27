package model.User;

import java.util.ArrayList;
import java.util.List;

import model.Database.database;
import model.Hostel.Hostel;
import model.Hostel.MaintenanceRequest;

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
        return database.getHostelsbyMaintenanceStaffId(userId);
    }

    @SuppressWarnings("static-access")
    public void editProfile(String name, String email, String phone, String password) {
        database db = new database();
        if (name != null && !name.isEmpty())
        {
            this.name = name;
            db.updateName(userId, name);
        }
        if (email != null && !email.isEmpty())
        {
            this.email = email;
            db.updateEmail(userId, email);
        }
        if (phone != null && !phone.isEmpty())
        {
            this.phoneNumber = phone;
            db.updatePhone(userId, phone);
        }
        if (password != null && !password.isEmpty())
        {
            db.updatePassword(userId, password);
        }
        return;
    }

    public List<MaintenanceRequest> getRequests() {
        List<Hostel> hostels = getHostels();
        List<MaintenanceRequest> requests = new ArrayList<>();
        for (Hostel hostel : hostels) {
            List<MaintenanceRequest> requests2 = database.getMaintainaceRequests(hostel.getHostelId());
            for (MaintenanceRequest request : requests2) {
                requests.add(request);
            }
        }

        return requests;
    }
}
