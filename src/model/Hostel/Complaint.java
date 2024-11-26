package model.Hostel;

import model.Database.database;

public class Complaint {
    private String complaintId; // Unique identifier for the complaint
    private int roomNo; // ID of the room where the complaint originated
    private String hostelId; // ID of the hostel
    private String description; // Description of the complaint issue
    private String status; // Status of the complaint (e.g., "Pending", "Under Review", "Resolved")

    // Constructor
    public Complaint(String complaintId, int roomNo, String hostelId, String description) {
        this.complaintId = complaintId;
        this.roomNo = roomNo;
        this.hostelId = hostelId;
        this.description = description;
        this.status = "Pending"; // Default status set to "Pending"
    }

    // String requestId, int roomNo, String hostelId, String description, String status
    public Complaint(String complaintId, int roomNo, String hostelId, String description, String status) {
        this.complaintId = complaintId;
        this.roomNo = roomNo;
        this.hostelId = hostelId;
        this.description = description;
        this.status = status;
    }

    public Complaint(String complaintId)
    {
        Complaint complaint = database.getComplaint(complaintId);
        this.complaintId = complaint.complaintId;
        this.roomNo = complaint.roomNo;
        this.hostelId = complaint.hostelId;
        this.description = complaint.description;
        this.status = complaint.status;
    }

    // Getters and Setters
    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomId(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getHostelId() {
        return hostelId;
    }

    public void setHostelId(String hostelId) {
        this.hostelId = hostelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to mark the complaint as "Under Review"
    public void markUnderReview() {
        this.status = "Under Review";
    }

    // Method to mark the complaint as "Resolved"
    public void markResolved() {
        this.status = "Resolved";
    }

    public boolean saveComplaint() {

        return database.saveComplaint(this);
    }

    public String getRoomNoString() {
        return String.valueOf(roomNo);
    }

    public boolean resolve() {
        if (this.getStatus().equals("Pending")) {
            database.setComplaintStatus(complaintId, "Resolved");
            this.setStatus("Resolved");
            return true;
        }
        return false;
    }

}
