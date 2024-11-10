package model.Hostel;

public class RoomModel {
    private String roomId; // Unique identifier for the room
    private int maxBeds; // Maximum number of beds in the room
    private int freeSpace; // Available beds in the room

    public RoomModel(String roomId, int maxBeds) {
        this.roomId = roomId;
        this.maxBeds = maxBeds;
        this.freeSpace = maxBeds; // Initially, free space is equal to max beds
    }

    // Getter and Setter for roomId
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    // Getter and Setter for maxBeds
    public int getMaxBeds() {
        return maxBeds;
    }

    public void setMaxBeds(int maxBeds) {
        this.maxBeds = maxBeds;
        this.freeSpace = Math.min(freeSpace, maxBeds); // Adjust free space if max beds changes
    }

    // Getter and Setter for freeSpace
    public int getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(int freeSpace) {
        if (freeSpace <= maxBeds && freeSpace >= 0) {
            this.freeSpace = freeSpace;
        } else {
            throw new IllegalArgumentException("Free space cannot exceed max beds or be negative.");
        }
    }

    // Method to check if there is available space
    public boolean hasAvailableSpace() {
        return freeSpace > 0;
    }

    // Method to occupy a bed
    public boolean occupyBed() {
        if (freeSpace > 0) {
            freeSpace--;
            return true;
        }
        return false; // No free space left
    }

    // Method to vacate a bed
    public boolean vacateBed() {
        if (freeSpace < maxBeds) {
            freeSpace++;
            return true;
        }
        return false; // All beds are already free
    }
}
