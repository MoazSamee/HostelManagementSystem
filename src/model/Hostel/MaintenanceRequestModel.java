package model.Hostel;

public class MaintenanceRequestModel {
    private String requestId; // Unique identifier for the maintenance request
    private String roomId; // ID of the room where maintenance is needed
    private String hostelId; // ID of the hostel
    private String description; // Description of the maintenance issue
    private String status; // Status of the request (e.g., "Pending", "In Progress", "Completed")

    // Constructor
    public MaintenanceRequestModel(String requestId, String roomId, String hostelId, String description) {
        this.requestId = requestId;
        this.roomId = roomId;
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

    // Method to mark the request as "In Progress"
    public void markInProgress() {
        this.status = "In Progress";
    }

    // Method to mark the request as "Completed"
    public void markCompleted() {
        this.status = "Completed";
    }
}
