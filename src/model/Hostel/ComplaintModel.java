package model.Hostel;

public class ComplaintModel {
    private String complaintId; // Unique identifier for the complaint
    private String roomId; // ID of the room where the complaint originated
    private String hostelId; // ID of the hostel
    private String description; // Description of the complaint issue
    private String status; // Status of the complaint (e.g., "Pending", "Under Review", "Resolved")

    // Constructor
    public ComplaintModel(String complaintId, String roomId, String hostelId, String description) {
        this.complaintId = complaintId;
        this.roomId = roomId;
        this.hostelId = hostelId;
        this.description = description;
        this.status = "Pending"; // Default status set to "Pending"
    }

    // Getters and Setters
    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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
}
