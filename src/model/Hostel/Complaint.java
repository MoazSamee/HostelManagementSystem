package model.Hostel;

import model.Database.database;

public class Complaint {
    private String userId; // Unique identifier for the complaint
    private int roomNo; // ID of the room where the complaint originated
    private String hostelId; // ID of the hostel
    private String description; // Description of the complaint issue
    private String status; // Status of the complaint (e.g., "Pending", "Under Review", "Resolved")

    // Constructor
    public Complaint(String userId, int roomNo, String hostelId, String description) {
        this.userId = userId;
        this.roomNo = roomNo;
        this.hostelId = hostelId;
        this.description = description;
        this.status = "Pending"; // Default status set to "Pending"
    }

    // Getters and Setters
    public String getComplaintId() {
        return userId;
    }

    public void setComplaintId(String userId) {
        this.userId = userId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomId(String roomId) {
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
}
