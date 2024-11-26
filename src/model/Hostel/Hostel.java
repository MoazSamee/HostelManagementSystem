package model.Hostel;

import java.util.ArrayList;
import java.util.List;

import model.Database.database;
import model.User.MaintenanceStaffModel;
import model.User.StudentModel;

public class Hostel {
    private String hostelId;
    private String hostelName;
    private String hostelLocation;
    private List<StudentModel> students = new ArrayList<>();
    private List<MaintenanceStaffModel> maintenanceStaff;
    private List<Room> rooms;
    // private List<StudentModel> pendingApplication = new ArrayList<>();

    public Hostel(String hostelId, String hostelName,String hostelLocation) {
        this.hostelId = hostelId;
        this.hostelName = hostelName;
        this.maintenanceStaff = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.hostelLocation=hostelLocation;
    }

    public Hostel(String hostelId2) {
        Hostel hostel = database.gHostelbyID(hostelId2);
        this.hostelId = hostel.getHostelId();
        this.hostelName = hostel.getHostelName();
        this.hostelLocation = hostel.getHostelLocation();
        this.students = hostel.getStudents();
        this.maintenanceStaff = hostel.getMaintenanceStaff();
        this.rooms = hostel.getRooms();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.hasAvailableSpace()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getHostelLocation() {
        return hostelLocation;
    }

    // public List<StudentModel> getPendingApplication() {
    //     return pendingApplication;
    // }

    // public void addPendingApplication(StudentModel student) {
    //     pendingApplication.add(student);
    // }

    // public void removePendingApplication(StudentModel student) {
    //     pendingApplication.remove(student);
    // }
    
    public void setHostelLocation(String hostelLocation) {
        this.hostelLocation = hostelLocation;
    }
    
    public void appendRooms(List<Room> newRooms) {
        this.rooms.addAll(newRooms);
    }

    public void addStudent(StudentModel student) {
        students.add(student);
    }

    public void addMaintenanceStaff(MaintenanceStaffModel staff) {
        maintenanceStaff.add(staff);
    }

    public String getHostelId() {
        return hostelId;
    }

    public void setHostelId(String hostelId) {
        this.hostelId = hostelId;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public List<StudentModel> getStudents() {
        return database.getStudentsByHostel(hostelId);
    }

    public void setStudents(List<StudentModel> students) {
        this.students = students;
    }

    public List<MaintenanceStaffModel> getMaintenanceStaff() {
        return database.getMaintenanceStaffByHostel(hostelId);
    }

    public void setMaintenanceStaff(List<MaintenanceStaffModel> maintenanceStaff) {
        this.maintenanceStaff = maintenanceStaff;
    }

    public List<String[]> getPendingApplication() {
        return database.getRoomRequests(hostelId);
    }

}
