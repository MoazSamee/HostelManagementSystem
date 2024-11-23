package model.Hostel;

import java.util.ArrayList;
import java.util.List;
import model.User.MaintenanceStaffModel;
import model.User.StudentModel;

public class Hostel {
    private String hostelId;
    private String hostelName;
    private String hostelLocation;
    private List<StudentModel> students = new ArrayList<>();
    private List<MaintenanceStaffModel> maintenanceStaff;
    private List<RoomModel> rooms;
    private List<StudentModel> pendingApplication = new ArrayList<>();

    public Hostel(String hostelId, String hostelName,String hostelLocation) {
        this.hostelId = hostelId;
        this.hostelName = hostelName;
        this.maintenanceStaff = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.hostelLocation=hostelLocation;

    }

    public List<RoomModel> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomModel> rooms) {
        this.rooms = rooms;
    }

    public String getHostelLocation() {
        return hostelLocation;
    }

    public List<StudentModel> getPendingApplication() {
        return pendingApplication;
    }

    public void addPendingApplication(StudentModel student) {
        pendingApplication.add(student);
    }

    public void removePendingApplication(StudentModel student) {
        pendingApplication.remove(student);
    }
    public void setHostelLocation(String hostelLocation) {
        this.hostelLocation = hostelLocation;
    }
    // Method to append a list of rooms to the existing list
    public void appendRooms(List<RoomModel> newRooms) {
        this.rooms.addAll(newRooms);
    }

    // Method to add a student to the hostel
    public void addStudent(StudentModel student) {
        students.add(student);
    }

    // Method to add maintenance staff to the hostel
    public void addMaintenanceStaff(MaintenanceStaffModel staff) {
        maintenanceStaff.add(staff);
    }

    // Getters and Setters
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
        return students;
    }

    public void setStudents(List<StudentModel> students) {
        this.students = students;
    }

    public List<MaintenanceStaffModel> getMaintenanceStaff() {
        return maintenanceStaff;
    }

    public void setMaintenanceStaff(List<MaintenanceStaffModel> maintenanceStaff) {
        this.maintenanceStaff = maintenanceStaff;
    }
}
