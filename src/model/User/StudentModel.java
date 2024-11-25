package model.User;

import java.util.List;

import model.Database.database;
import model.Database.mysql;
import model.Hostel.Hostel;
import model.Hostel.MaintenanceRequestModel;
import model.Hostel.Room;

public class StudentModel extends UserModel {
    private String address;
    private String universityOrJob;
    private String organizationAddress;
    private String hostelId;
    private int roomNo;

    public StudentModel(String userId, String name, String email, String phoneNumber, String address, String universityOrJob, String organizationAddress) {
        super(userId, name, email, phoneNumber);
        this.address = address;
        this.universityOrJob = universityOrJob;
        this.organizationAddress = organizationAddress;
        this.roomNo = -1;
        this.hostelId = null;
    }
    
    public StudentModel(String userId, String name, String email, String phoneNumber, String hostelId,int roomNo, String address, String universityOrJob, String organizationAddress) {
        super(userId, name, email, phoneNumber);
        this.address = address;
        this.universityOrJob = universityOrJob;
        this.organizationAddress = organizationAddress;
        this.roomNo = roomNo;
        this.hostelId = hostelId;
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniversityOrJob() {
        return universityOrJob;
    }

    public void setUniversityOrJob(String universityOrJob) {
        this.universityOrJob = universityOrJob;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getHostelId() {
        return hostelId;
    }

    public void setHostelId(String hostelId) {
        this.hostelId = hostelId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public void onMaintenanceRequestSubmitted(String complaint, String details) {
    
        // Validate hostelId and roomNo
        if (hostelId == null || hostelId.isEmpty()) {
            System.out.println("Error: Hostel ID is missing for the student.");
            return;
        }

        if (roomNo == -1) {
            System.out.println("Error: No room assigned to the student.");
            return;
        }

        // Create a new MaintenanceRequestModel
        String requestId = java.util.UUID.randomUUID().toString(); // Generate a unique request ID
        
        MaintenanceRequestModel request = new MaintenanceRequestModel(requestId, roomNo, hostelId, details);
        request.setDescription(complaint); // Set the complaint description

        // Save the request to the database
        mysql database = new mysql();
        boolean isSaved = database.saveMaintenanceRequest(request);

        // Notify the user of the result
        if (isSaved) {
            System.out.println("Maintenance Request Submitted Successfully:");
            System.out.println("Student Name: " + name);
            System.out.println("Hostel: " + hostelId);
            System.out.println("Room: " + roomNo);
            System.out.println("Complaint: " + complaint);
            System.out.println("Details: " + details);
        } else {
            System.out.println("Failed to Submit Maintenance Request.");
        }
    }

    public List<Hostel> getHostels()
    {
        return database.getHostels();
    }

    public List<Hostel> getHostels(String hostel_name)
    {
        return database.getHostels(hostel_name);
    }

    @SuppressWarnings("static-access")
    public void editProfile(String name, String email, String phone, String password) {
        database db = new database();
        if (name != null && !name.isEmpty()) {
            this.name = name;
            db.updateName(userId, name);
        }
        if (email != null && !email.isEmpty()) {
            this.email = email;
            db.updateEmail(userId, email);
        }
        if (phone != null && !phone.isEmpty()) {
            this.phoneNumber = phone;
            db.updatePhone(userId, phone);
        }
        if (password != null && !password.isEmpty()) {
            db.updatePassword(userId, password);
        }
    }

    public void bookRoom(String hostelId, String roomId) {
        // Validate hostelId and roomNo
        if (hostelId == null || hostelId.isEmpty()) {
            System.out.println("Error: Hostel ID is missing for the student.");
            return;
        }

        if (roomId == null || roomId.isEmpty()) {
            System.out.println("Error: No room assigned to the student.");
            return;
        }

        System.out.println("Student ID: " + userId);
        System.out.println("Hostel: " + hostelId);
        System.out.println("Room: " + roomId);

        boolean isSaved = database.bookRoomRequwst(userId, hostelId, roomId);

        if (isSaved) {
            System.out.println("Room Booked Successfully:");

        } else {
            System.out.println("Failed to Book Room.");
        }
    }

    public Hostel getHostelById(String hostelid) {
        return database.gHostelbyID(hostelid);
    }

    public void refreshDetails() {
        this.organizationAddress = database.getOrganizationAddress(userId);
        this.universityOrJob = database.getUniversityOrJob(userId);
        this.address = database.getAddress(userId);
    }

    public Hostel getCurrunthostel() {

        return database.gHostelbyStudentID(userId);

    }

    public Room getCurrunthostelRoom() {
        return database.getRoomByStudentID(userId);
    }

}
