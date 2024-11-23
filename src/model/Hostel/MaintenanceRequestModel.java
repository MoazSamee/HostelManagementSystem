package model.Hostel;

public class MaintenanceRequestModel {
    private String requestId; // Unique identifier for the maintenance request
    private int  roomNo; // ID of the room where maintenance is needed
    private String hostelId; // ID of the hostel
    private String description; // Description of the maintenance issue
    private String status; // Status of the request (e.g., "Pending", "In Progress", "Completed")

    // Constructor
    public MaintenanceRequestModel(String requestId, int roomNo, String hostelId, String description) {
        this.requestId = requestId;
        this.roomNo = roomNo;
        this.hostelId = hostelId;
        this.description = description;
        this.status = "Pending"; // Default status set to "Pending"
    }

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public String getRoomNoString() {
        return String.valueOf(roomNo);
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

    // Method to mark the request as "In Progress"
    public void markInProgress() {
        this.status = "In Progress";
    }

    // Method to mark the request as "Completed"
    public void markCompleted() {
        this.status = "Completed";
    }
}
