package controller.Student;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Hostel.Hostel;
import model.Hostel.Room;
import model.User.StudentModel;
import view.Student.BookHostel;
import view.comman.HostelCard;

public class StudentController {
    private StudentModel student;

    private GridPane gridPane;
    private List<HostelCard> hostelCards;


    @SuppressWarnings("unused")
    public StudentController(StudentModel user) {
        this.student = user;
        user.refreshDetails();
        this.hostelCards = new ArrayList<>();
        if (student != null) {
            List<Hostel> hostels = student.getHostels();

            for (Hostel hostel : hostels) {
                HostelCard card = new HostelCard(hostel.getHostelName(), hostel.getHostelLocation(), hostel.getRooms().size(), hostel.getStudents().size());
                card.setOnMouseClicked(e -> cardClicked(hostel.getHostelId()));
                hostelCards.add(card);
            }
        }
    }

    // Done
    public void onSearchButtonClicked(String searchQuery) {
        populateInitialGridContent();
        refreshGridContent((int)gridPane.getWidth());
    }

    // Done
    @SuppressWarnings("unused")
    public void onSearchTextChanged(String newText) {
        hostelCards.clear();

        List<Hostel> hostels = student.getHostels(newText);

        for (Hostel hostel : hostels) {
            HostelCard card = new HostelCard(hostel.getHostelName(),hostel.getHostelLocation(), hostel.getRooms().size(), hostel.getStudents().size());
            card.setOnMouseClicked(e -> cardClicked(hostel.getHostelId()));
            hostelCards.add(card);
        }
        refreshGridContent((int)gridPane.getWidth());
    }

    // Done    
    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    // Done    
    @SuppressWarnings("unused")
    public void populateInitialGridContent() {
        if (gridPane == null) {
            System.err.println("GridPane is not set.");
            return;
        }
        hostelCards.clear();

        List<Hostel> hostels = student.getHostels();
        for (Hostel hostel : hostels) {
            HostelCard card = new HostelCard(hostel.getHostelName(),hostel.getHostelLocation(), hostel.getAvailableRooms().size(), hostel.getStudents().size());
            System.out.println("Hostel: " + hostel.getHostelName() + " - " + hostel.getAvailableRooms().size() + " - " + hostel.getStudents().size());
            card.setOnMouseClicked(e -> cardClicked(hostel.getHostelId()));
            hostelCards.add(card);
        }

    }

    // Done
    public void refreshGridContent(int Width) {
        if (gridPane == null) {
            System.err.println("GridPane is not set.");
            return;
        }

        gridPane.getChildren().clear();
        for (HostelCard card : hostelCards) {
            addBox(card, Width);
        } 
    }

    // Done
    public void cardClicked(String hostelid) {//mysql getHostelById
        System.out.println("Clicked on: " + hostelid);

        Hostel hostel = student.getHostelById(hostelid);
        BookHostel bookHostel = new BookHostel(hostel);
        bookHostel.start(new Stage());

        Platform.runLater(() -> {
            Room rooms = bookHostel.getSelectedRoom();
            if (rooms != null) {
                if(student.bookRoom(hostelid, rooms.getRoomId()))
                {
                    JOptionPane.showMessageDialog(null, "Room Booked", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Room Not Booked", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }

    // Done
    public void onMaintenanceRequestSubmitted(String complaint, String maintenance) {
        if (maintenance != null && !maintenance.isEmpty() && complaint != null && !complaint.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (maintenance != null && !maintenance.isEmpty())
        {
            if (student.submitMaintenanceRequest(maintenance))
            {
                JOptionPane.showMessageDialog(null, "Maintenance Request Submitted", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Maintenance Request Not Submitted", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (complaint != null && !complaint.isEmpty())
        {
            if (student.submitComplaint(complaint))
            {
                JOptionPane.showMessageDialog(null, "Complaint Submitted", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Complaint Not Submitted", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Done
    public void editProfile(String name, String email, String phone, String univercity, String address, String orgAddress, String password, String password2) {
        System.out.println("Profile Edited: " + name + " - " + email + " - " + phone + " - " + password + " - " + password2);
        if (password.equals(password2))
        {
            student.editProfile(name, email, phone, univercity, address, orgAddress, password);
            JOptionPane.showMessageDialog(null, "Profile Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            System.out.println("Passwords do not match");
            JOptionPane.showMessageDialog(null, "Password do not match", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Done
    public void setStudent(StudentModel student) {
        student.refreshDetails();
        this.student = student;
        
    }
    
    // Done
    private void addBox(HostelCard card, int gridPaneWidth) {
        if (gridPane == null) {
            System.err.println("GridPane is not set.");
            return;
        }
        
        int cardWidth = card.getCardWidth();
        int columns = (gridPaneWidth + cardWidth) > 0 ? (int) ((gridPaneWidth + cardWidth) / cardWidth) : 1;
        columns = Math.max(1, columns);

        int index = gridPane.getChildren().size();
        int row = index / columns;
        int col = index % columns;

        gridPane.add(card, col, row);
    }

    public String getStudentName() {
        if (student == null) {
            return "Student";
        }
        return student.getName();
    }

    public String getStudentEmail() {
        if (student == null) {
            return "";
        }
        return student.getEmail();
    }

    public String getStudentPhone() {
        if (student == null) {
            return "";
        }
        return student.getPhoneNumber();
    }

    public String getStudentUniversity() {
        if (student == null) {
            return "";
        }
        return student.getUniversityOrJob();
    }

    public String getStudentAddress() {
        if (student == null) {
            return "";
        }
        return student.getAddress();
    }

    public String getStudentOrgAddress() {
        if (student == null) {
            return "";
        }
        return student.getOrganizationAddress();
    }

    public String getCurruntStudentHostelname() {
        if (student == null) {
            return "No Hostel";
        }
        Hostel hostel = student.getCurrunthostel();
        if (hostel == null) {
            return "No Hostel";
        }
        return hostel.getHostelName();
    }

    public String getCurruntStudentHostelRoom() {
        if (student == null) {
            return "No Room";
        }
        Room room = student.getCurrunthostelRoom();
        if (room == null) {
            return "No Room";
        }
        return String.valueOf(room.getRoomNo());
    }

    public String getCurruntStudentHostelAdress() {
        if (student == null) {
            return "No Hostel";
        }
        Hostel hostel = student.getCurrunthostel();
        if (hostel == null) {
            return "No Hostel";
        }
        return hostel.getHostelLocation();
    }

}
