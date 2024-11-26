package controller.MaintenanceStaff;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Hostel.Hostel;
import model.Hostel.MaintenanceRequest;
import model.User.MaintenanceStaffModel;

public class MaintenanceStaffController {
    MaintenanceStaffModel user;

    public MaintenanceStaffController(MaintenanceStaffModel user2) {
        this.user = user2;
    }

    // Done
    public String getUserName() {
        return user.getName();
    }

    // Done
    public String getUserPhone() {
        return user.getPhoneNumber();
    }

    // Done
    public String getEmail() {
        return user.getEmail();
    }
    
    // Done
    public void editProfile(String name, String email, String phone, String password, String password2) {
        System.out.println("Profile Edited: " + name + " - " + email + " - " + phone + " - " + password + " - " + password2);
        if (password.equals(password2))
        {
            user.editProfile(name, email, phone, password);
            JOptionPane.showMessageDialog(null, "Profile Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            System.out.println("Passwords do not match");
            JOptionPane.showMessageDialog(null, "Password do not match", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return;
    }

    // Done
    // userid, hostel name, room number,hostel adress , issue, requestid
    public List<String[]> fetchRequests() {
        List<String[]> requests = new ArrayList<String[]>();
        List<MaintenanceRequest> requests2 = user.getRequests();
        for (MaintenanceRequest request : requests2) {
            Hostel hostel = new Hostel(request.getHostelId());
            requests.add(new String[] { request.getRoomNoString(), hostel.getHostelName(), hostel.getHostelLocation(),request.getStatus(), request.getDescription(), request.getuserId()});
        }
        // sort requests by status In Progress, Pending, Completed
        requests.sort((a, b) -> {
            if (a[3].equals("Completed") && !b[3].equals("Completed")) {
            return 1;
            } else if (!a[3].equals("Completed") && b[3].equals("Completed")) {
            return -1;
            } else {
            return a[3].compareTo(b[3]);
            }
        });
        return requests;
    }

    // Done
    public void handleResolve(String[] request) {
        MaintenanceRequest request2 = new MaintenanceRequest(request[5]);
        System.out.println("Request Resolved: " + request2.getuserId() + " - " + request2.getHostelId() + " - " + request2.getRoomNoString() + " - " + request2.getDescription() + " - " + request2.getStatus());
        if (!request2.resolve())
        {
            JOptionPane.showMessageDialog(null, "Request could not be resolved", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return;
    }

    // Done
    public void setStudent(MaintenanceStaffModel user2) {
        this.user = user2;
    }

}
