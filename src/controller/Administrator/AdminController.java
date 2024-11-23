package controller.Administrator;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.stage.Stage;
import view.comman.AddStaffDialog;
import view.comman.GetIdDialog;

public class AdminController {

    public List<String[]> fetchRoomRequests() {
        List<String[]> roomRequests = new ArrayList<>();
        roomRequests.add(new String[] { "Alice", "001", "Hostel A", "Room 101" });
        roomRequests.add(new String[] { "Bob", "002", "Hostel B", "Room 202" });
        roomRequests.add(new String[] { "Charlie", "003", "Hostel C", "Room 303" });
        roomRequests.add(new String[] { "Charlie", "003", "Hostel C", "Room 303" });
        roomRequests.add(new String[] { "Charlie", "003", "Hostel C", "Room 303" });
        roomRequests.add(new String[] { "Alice", "001", "Hostel A", "Room 101" });
        roomRequests.add(new String[] { "Bob", "002", "Hostel B", "Room 202" });
        roomRequests.add(new String[] { "Charlie", "003", "Hostel C", "Room 303" });
        roomRequests.add(new String[] { "Charlie", "003", "Hostel C", "Room 303" });
        roomRequests.add(new String[] { "Charlie", "003", "Hostel C", "Room 303" });
        return roomRequests;
    }

    public void approveRequest(String name) {
        System.out.println("Approved request for " + name);
    }

    public void disapproveRequest(String name) {
        System.out.println("Disapproved request for " + name);
    }

    public void removeStudent(String studentId) {
        System.out.println("Removed student with ID " + studentId);
    }

    public List<String[]> fetchStudents() {
        List<String[]> students = new ArrayList<>();
        // headers = { "ID", "Name", "Email", "Phone", "Address", "University/Job"
        students.add(new String[] { "001", "Alice", "alice@email.com", "123456789", "123, ABC Street, XYZ City",
                "University of ABC" });
        students.add(
                new String[] { "002", "Bob", "cmsmo", "123456789", "123, ABC Street, XYZ City", "University of ABC" });
        students.add(new String[] { "003", "Charlie", "cmsmo", "123456789", "123, ABC Street, XYZ City",
                "University of ABC" });
        students.add(new String[] { "004", "Alice", "cmsmo", "123456789", "123, ABC Street, XYZ City",
                "University of ABC" });
        students.add(
                new String[] { "005", "Bob", "cmsmo", "123456789", "123, ABC Street, XYZ City", "University of ABC" });
        students.add(new String[] { "006", "Charlie", "cmsmo", "123456789", "123, ABC Street, XYZ City",
                "University of ABC" });
        students.add(new String[] { "007", "Alice", "cmsmo", "123456789", "123, ABC Street, XYZ City",
                "University of ABC" });
        students.add(
                new String[] { "008", "Bob", "cmsmo", "123456789", "123, ABC Street, XYZ City", "University of ABC" });
        return students;
    }

    public List<String[]> fetchHostels() {
        List<String[]> hostels = new ArrayList<>();
        // String hostelId = hostel[0];
        // String hostelName = hostel[1];
        // int numberPendingApplications = Integer.parseInt(hostel[2]);
        // String[] students = hostel[3].split(",");
        // String[] maintenanceStaff = hostel[4].split(",");
        hostels.add(new String[] { "001", "Hostel A", "3", "Alice,Bob,Charlie", "John,Smith" });
        hostels.add(new String[] { "002", "Hostel B", "2", "Alice,Bob", "John,Smith" });
        hostels.add(new String[] { "003", "Hostel C", "1", "Alice", "John,Smith" });
        hostels.add(new String[] { "004", "Hostel D", "0", "", "John,Smith" });
        return hostels;
    }

    public void removeHostel(String hostelId) {
        System.out.println("Removed hostel with ID " + hostelId);
    }

    public void editProfile(String name, String email, String phone, String password, String password2) {
        System.out.println("Edited profile");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Password: " + password);
        System.out.println("Password2: " + password2);
    }

    public void addStaffToHostel(String hostelId) {
        System.out.println("Added staff to hostel with ID " + hostelId);
        
        AddStaffDialog dialog = new AddStaffDialog();
        dialog.start(new Stage());
        
        Platform.runLater(() -> {
            List<String> data = dialog.getData();

            if (data != null && data.size() == 3) {
                String name = data.get(0);
                String email = data.get(1);
                String password = data.get(2);

                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);

                System.out.println("Staff successfully added to hostel.");
            } else {
                System.out.println("Dialog was canceled or closed.");
            }
        });
    }
    

    public void removeStaffFromHostel(String hostelId) {
        System.out.println("Removed staff from hostel with ID " + hostelId);

        GetIdDialog dialog = new GetIdDialog();
        dialog.start(new Stage());

        Platform.runLater(() -> {
            String id = dialog.getId().orElse(null);

            if (id != null) {
                System.out.println("ID: " + id);

                System.out.println("Staff successfully removed from hostel.");
            } else {
                System.out.println("Dialog was canceled or closed.");
            }
        });
    }
}
