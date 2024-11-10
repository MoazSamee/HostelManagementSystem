package model.Hostel;

import model.User.StudentModel;
import model.User.MaintenanceStaffModel;
import java.util.ArrayList;
import java.util.List;

public class Hostel {
    private String hostelId;
    private String hostelName;
    private List<StudentModel> students;
    private List<MaintenanceStaffModel> maintenanceStaff;
    private List<StudentModel> pendingApplications;

    public Hostel(String hostelId, String hostelName) {
        this.hostelId = hostelId;
        this.hostelName = hostelName;
        this.students = new ArrayList<>();
        this.maintenanceStaff = new ArrayList<>();
        this.pendingApplications = new ArrayList<>();
    }

    // Method to add a student to the pending applications
    public void applyForHostel(StudentModel student) {
        pendingApplications.add(student);
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

    public List<StudentModel> getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(List<StudentModel> pendingApplications) {
        this.pendingApplications = pendingApplications;
    }
}
