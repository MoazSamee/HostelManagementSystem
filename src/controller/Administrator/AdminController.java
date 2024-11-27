package controller.Administrator;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.stage.Stage;
import model.Hostel.Complaint;
import model.Hostel.Hostel;
import model.Hostel.Room;
import model.User.AdministratorModel;
import model.User.MaintenanceStaffModel;
import model.User.StudentModel;
import view.Administrator.AddNewRoomPage;
import view.comman.AddNewHostel;
import view.comman.AddStaffDialog;
import view.comman.GetIdDialog;

public class AdminController {
    private static AdministratorModel user;

    public AdminController(AdministratorModel user) {
        AdminController.user = user;
    }

    // Done
    public List<String[]> fetchRoomRequests() {
        List<String[]> roomRequests = user.getRoomRequests();
        return roomRequests;
    }

    // Done
    public void approveRequest(String rewuestID) {
        System.out.println("Approved request for " + rewuestID);
        if (user.approveRoomRequest(rewuestID)) {
            System.out.println("Request approved successfully.");
        } else {
            System.out.println("Request could not be approved.");
        }
    }

    // Done
    public void disapproveRequest(String requestID) {
        System.out.println("Disapproved request for " + requestID);
        if (user.disapproveRoomRequest(requestID)) {
            System.out.println("Request disapproved successfully.");
        } else {
            System.out.println("Request could not be disapproved.");
        }
    }

    // Done
    public void removeStudent(String studentId) {
        // Asking for coinfermation
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove student with ID " + studentId + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) 
        {    
            if (user.removeStudent(studentId)) {
                JOptionPane.showMessageDialog(null, "Student removed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Student could not be removed.");
            }
        }
    }

    // Done
    public List<String[]> fetchStudents() {
        // headers = { "ID", "Name", "Email", "Phone", "Address", "University/Job"
        // students.add(new String[] { "001", "Alice", "alice@email.com", "123456789", "123, ABC Street, XYZ City",
        //         "University of ABC" });
        // students.add(
        //         new String[] { "002", "Bob", "cmsmo", "123456789", "123, ABC Street, XYZ City", "University of ABC" });
        // students.add(new String[] { "003", "Charlie", "cmsmo", "123456789", "123, ABC Street, XYZ City",
        //         "University of ABC" });
        // students.add(new String[] { "004", "Alice", "cmsmo", "123456789", "123, ABC Street, XYZ City",
        //         "University of ABC" });
        // students.add(
        //         new String[] { "005", "Bob", "cmsmo", "123456789", "123, ABC Street, XYZ City", "University of ABC" });
        // students.add(new String[] { "006", "Charlie", "cmsmo", "123456789", "123, ABC Street, XYZ City",
        //         "University of ABC" });
        // students.add(new String[] { "007", "Alice", "cmsmo", "123456789", "123, ABC Street, XYZ City",
        //         "University of ABC" });
        // students.add(
            //         new String[] { "008", "Bob", "cmsmo", "123456789", "123, ABC Street, XYZ City", "University of ABC" });
            
            
        List<String[]> students = new ArrayList<>();
        List<StudentModel> studentsbyUser = user.getStudents();
        for (StudentModel student : studentsbyUser) {
            students.add(new String[] { student.getUserId(), student.getName(), student.getEmail(), student.getPhoneNumber(),
                    student.getAddress(), student.getUniversityOrJob() });
        }
        return students;
    }

    // Done
    public List<String[]> fetchHostels() {
        List<String[]> hostels = new ArrayList<>();

        List<Hostel> hostelsbyUser = user.getHostels();
        for (Hostel hostel : hostelsbyUser) {
            String staffNames = "";
            List<MaintenanceStaffModel> staffs = hostel.getMaintenanceStaff();
            for (MaintenanceStaffModel staff : staffs) {
                staffNames += "(" + staff.getUserId() + ")" +staff.getName()+ ",";
            }
            String studentsName = "";
            List<StudentModel> students = hostel.getStudents();
            for (StudentModel student : students) {
                studentsName += student.getName() + ",";
            }
            String rooms = "";
            List<Room> roomsList = hostel.getRooms();
            for (Room room : roomsList) {
                if (room.getFreeSpace() == 0) {
                    rooms += "Room " + room.getRoomNo() + " (✔️),";
                }
                else if (room.getFreeSpace() == room.getMaxBeds()) {
                    // waiting icon
                    rooms += "Room " + room.getRoomNo() + " (⏳),";
                }
                else{
                    rooms += "Room " + room.getRoomNo() + " (" + room.getFreeSpace() + "/" + room.getMaxBeds() + "),";
                }
            }

            List<String[]> h =  hostel.getPendingApplication();
            System.out.println(h.size());
            hostels.add(new String[] { hostel.getHostelLocation(), hostel.getHostelName(), hostel.getPendingApplication().size() + "",
                studentsName, staffNames, rooms, hostel.getHostelId() });
        }

        return hostels;
    }

    // Done
    public void removeHostel(String hostelId) {
        System.out.println("Removed hostel with ID " + hostelId);

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove hostel with ID " + hostelId + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) 
        {    
            if (user.removeHostel(hostelId)) {
                JOptionPane.showMessageDialog(null, "Hostel removed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Hostel could not be removed.");
            }
        }
    }

    // Done
    public void addStaffToHostel(String hostelId) {
        
        AddStaffDialog dialog = new AddStaffDialog();
        dialog.start(new Stage());
        
        Platform.runLater(() -> {
            List<String> data = dialog.getData();

            if (data != null && data.size() == 2) {
                String username = data.get(0);
                String email = data.get(1);

                System.out.println("Name: " + username);
                System.out.println("Email: " + email);
                if (user.addStaffToHostel(hostelId, username, email)) {
                    JOptionPane.showMessageDialog(null, "Staff added successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Staff could not be added.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Dialog was canceled or closed.");
            }
        });
    }
    
    // Done
    public void removeStaffFromHostel(String hostelId) {
        GetIdDialog dialog = new GetIdDialog();
        dialog.start(new Stage());

        Platform.runLater(() -> {
            String id = dialog.getId().orElse(null);

            if (id != null) {
                System.out.println("ID: " + id);
                System.out.println("Staff removed from hostel." + hostelId);
                if (user.removeStaffFromHostel(hostelId, id)) {
                    JOptionPane.showMessageDialog(null, "Staff removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Staff could not be removed.");
                }
            } else {
                System.out.println("Dialog was canceled or closed.");
            }
        });
    }

    // Done
    public void addHostel() {

        AddNewHostel dialog = new AddNewHostel();
        dialog.start(new Stage());

        Platform.runLater(() -> {
            List<String> data = dialog.getData();

            if (data != null && data.size() == 3) {
                String hostelId = data.get(0);
                String hostelName = data.get(1);
                String hostelLocation = data.get(2);
        
                System.out.println("Hostel Name: " + hostelName);
                System.out.println("Hostel Location: " + hostelLocation);
                if (user.addHostel(hostelId, hostelName, hostelLocation)) {
                    JOptionPane.showMessageDialog(null, "Hostel added successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Hostel could not be added.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Dialog was canceled or closed.");
            }
        });
    }

    // Done
    public void editProfile(String name, String email, String phone, String password, String password2) {
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
    }

    public String getUserName() {
        if (user != null) {
            return user.getName();
        }
        return null;
    }

    public String getUserEmail() {
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    public String getUserPhone() {
        if (user != null) {
            return user.getPhoneNumber();
        }
        return null;
    }

    public List<String[]> fetchRequests() {
        List<String[]> requests = new ArrayList<String[]>();
        List<Complaint> requests2 = user.getRequests();
        for (Complaint request : requests2) {
            Hostel hostel = new Hostel(request.getHostelId());
            requests.add(new String[] { request.getRoomNoString(), hostel.getHostelName(), hostel.getHostelLocation(),request.getStatus(), request.getDescription(), request.getComplaintId()});
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

    public void handleResolve(String[] request) {
        Complaint request2 = new Complaint(request[5]);
        System.out.println("Reqest : " + request[5]);
        if (!request2.resolve())
        {
            JOptionPane.showMessageDialog(null, "Request could not be resolved", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return;
    }

    public void addRoomToHostel(String hostelId) {
        AddNewRoomPage dialog = new AddNewRoomPage();
        dialog.start(new Stage());

        Platform.runLater(() -> {
            List<String> data = dialog.getData();

            if (data != null && data.size() == 2) {
                String roomNumber = data.get(0);
                String maxBeds = data.get(1);

                System.out.println("Room Number: " + roomNumber);
                System.out.println("Max Beds: " + maxBeds);
                if (user.addRoomToHostel(hostelId, roomNumber, maxBeds)) {
                    JOptionPane.showMessageDialog(null, "Room added successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Room could not be added.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Dialog was canceled or closed.");
            }
        });
    }
}
