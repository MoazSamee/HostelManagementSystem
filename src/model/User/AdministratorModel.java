package model.User;

import model.Database.database;
import model.Hostel.Complaint;
import model.Hostel.Hostel;

import java.util.ArrayList;
import java.util.List;

public class AdministratorModel extends UserModel {
    private List<Hostel> hostels= new ArrayList<Hostel>();

    public void appendHostel(Hostel hostel) {
        hostels.add(hostel);
    }

    public AdministratorModel(String userId, String name, String email, String phoneNumber, String hostelId) {
        super(userId, name, email, phoneNumber);
    }
    

    @Override
    public List<Hostel> getHostels() {
        return database.getHostelsbyAdminId(userId);        
    }

    // { "User name", "user phone", "Hostel Name", "Room number" })
    public List<String[]> getRoomRequests() {
        List<Hostel> hostels = database.getHostelsbyAdminId(userId);
        List<String[]> roomRequests = new ArrayList<>();
        System.err.println(hostels);
        for (Hostel hostel : hostels) {
            List<String[]> requests = database.getRoomRequests(hostel.getHostelId());
            for (String[] request : requests) {
                roomRequests.add(new String[] { request[0], request[1], hostel.getHostelName(), request[2] ,request[3]});
            }
        }
        return roomRequests;
    }

    public boolean approveRoomRequest(String requestID) {
        if (requestID != null)
        {
            return database.approveRoomRequest(requestID);
        }
        return false;
        
    }

    public boolean disapproveRoomRequest(String requestID) {
        if (requestID != null)
        {
            return database.disapproveRoomRequest(requestID);
        }
        return false;
    }

    public List<StudentModel> getStudents() {
        List<Hostel> hostels = database.getHostelsbyAdminId(userId);
        List<StudentModel> studentsList = new ArrayList<>();
        for (Hostel hostel : hostels) {
            List<StudentModel> students = database.getStudentsByHostel(hostel.getHostelId());
            for (StudentModel student : students) {
                studentsList.add(student);
            }
        }
        return studentsList;
    }

    public boolean removeStudent(String studentId) {
        if (studentId != null)
        {
            return database.removeStudent(studentId);
        }
        return false;
    }

    public boolean removeHostel(String hostelId) {
        if (hostelId != null)
        {
            return database.removeHostel(hostelId, userId);
        }
        return false;
    }

    public boolean addStaffToHostel(String hostelId, String username, String email) {
        if (hostelId != null && username != null && email != null)
        {
            System.out.println("Adding staff to hostel with ID " + hostelId);

            return database.addStaffToHostel(hostelId, username, email);
        }
        return false;
    }

    public boolean removeStaffFromHostel(String hostelId, String id) {
        if (hostelId != null && id != null)
        {
            System.out.println("Removing staff from hostel with ID " + hostelId);

            return database.removeStaffFromHostel(hostelId, id);
        }
        return false;
    }

    public boolean addHostel(String hostelId, String hostelName, String hostelLocation) {
        if (hostelId != null && hostelName != null && hostelLocation != null)
        {
            System.out.println("Adding hostel with ID " + hostelId + userId) ;

            return database.addHostel(hostelId, hostelName, hostelLocation, userId);
        }
        return false;
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
    }

    public List<Complaint> getRequests() {
        List<Hostel> hostels = database.getHostels();
        List<Complaint> requests = new ArrayList<>();
        for (Hostel hostel : hostels) {
            List<Complaint> complaints = database.getComplaints(hostel.getHostelId());
            for (Complaint complaint : complaints) {
                requests.add(complaint);
            }
        }
        return requests;
    }

    public boolean addRoomToHostel(String hostelId, String roomNumber, String maxBeds) {
        if (hostelId != null && roomNumber != null && maxBeds != null)
        {
            System.out.println("Adding room to hostel with ID " + hostelId);

            return database.addRoomToHostel(hostelId, roomNumber, maxBeds);
        }
        return false;
    }

}
