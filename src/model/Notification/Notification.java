package model.Notification;

public class Notification {
    private String notificationId; // Unique identifier for the notification
    private String userId; // The ID of the user who will receive the notification
    private String userType; // Type of user (e.g., Student, Maintenance Staff, Admin)
    private String description; // Description of the notification
    private String status; // Status of the notification (e.g., "Unread", "Read")

    // Constructor
    public Notification(String notificationId, String userId, String userType, String description) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.userType = userType;
        this.description = description;
        this.status = "Unread"; // Default status set to "Unread"
    }

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    // Method to mark the notification as "Read"
    public void markAsRead() {
        this.status = "Read";
    }

    // Method to display notification details
    @Override
    public String toString() {
        return "Notification [ID=" + notificationId + ", UserID=" + userId + ", UserType=" + userType + 
               ", Description=" + description + ", Status=" + status + "]";
    }
}
