package model.Hostel;

public class Room {
    private String roomId;
    private int maxBeds;
    private int freeSpace;
    private int roomNo;
    private Package roomPackage;

    public Room(String roomId, int maxBeds,int roomNo) {
        this.roomId = roomId;
        this.maxBeds = maxBeds;
        this.freeSpace = maxBeds;
        this.roomNo = roomNo;
    }

    public Room(String roomId, int maxBeds, int freeSpace, int roomNo) {
        this.roomId = roomId;
        this.maxBeds = maxBeds;
        this.freeSpace = freeSpace;
        this.roomNo = roomNo;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getMaxBeds() {
        return maxBeds;
    }

    public void setMaxBeds(int maxBeds) {
        this.maxBeds = maxBeds;
        this.freeSpace = Math.min(freeSpace, maxBeds);
    }

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
    // Getter and Setter for roomNo
    public int getRoomNo() {
        return roomNo;
    }
    
    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }
}