package controller.Student;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Hostel.Hostel;
import model.User.StudentModel;
import view.comman.HostelCard;

public class StudentController {
    StudentModel student;

    private final Text contentText;
    private GridPane gridPane;
    private List<HostelCard> hostelCards;

    // Done
    @SuppressWarnings("unused")
    public StudentController(Text contentText) {
        this.contentText = contentText;
        this.hostelCards = new ArrayList<>();
        //////////////////////////////////////////////////////////////////////// TBR
        // String[] hostelIds = {"H101", "H102", "H103"};
        // String[] hostelNames = {"Alpha Hostel", "Beta Hostel", "Gamma Hostel"};
        // int[] availableRooms = {10, 5, 12};
        // int[] students = {50, 45, 60};
        //////////////////////////////////////////////////////////////////////// TBR
        if (student != null) {
            List<Hostel> hostels = student.getHostel();

            for (Hostel hostel : hostels) {
                HostelCard card = new HostelCard(hostel.getHostelId(), hostel.getHostelName(), hostel.getRooms().size(), hostel.getStudents().size());
                card.setOnMouseClicked(e -> cardClicked(hostel.getHostelId()));
                hostelCards.add(card);
            }
        }
        // for (int i = 0; i < hostelIds.length; i++) {
        //     HostelCard card = new HostelCard(hostelIds[i], hostelNames[i], availableRooms[i], students[i]);
        //     final int index = i;
        //     card.setOnMouseClicked(e -> cardClicked(hostelIds[index]));
        //     hostelCards.add(card);
        // }
    }

    @SuppressWarnings("unused")
    public void onSearchButtonClicked(String searchQuery) { //mysql fetchAllHostels
        contentText.setText("Searching for: " + searchQuery);
        //////////////////////////////////////////////////////////////////////// TBR
        // HostelCard card = new HostelCard(searchQuery, "Delta Hostel", 15, 70);
        // card.setOnMouseClicked(e -> cardClicked(searchQuery));
        // hostelCards.add(card);
        // database.getHostels();
        //////////////////////////////////////////////////////////////////////// TBR
        
        populateInitialGridContent();
        // addBox(new HostelCard(searchQuery, "Delta Hostel", 15, 70));
    }

    // Done
    public void onSearchTextChanged(String newText) { //mysql getHostelByName
        contentText.setText("Search text changed: " + newText);
        hostelCards.clear();

        List<Hostel> hostels = student.getHostel(newText);

        for (Hostel hostel : hostels) {
            HostelCard card = new HostelCard(hostel.getHostelId(), hostel.getHostelName(), hostel.getRooms().size(), hostel.getStudents().size());
            card.setOnMouseClicked(e -> cardClicked(hostel.getHostelId()));
            hostelCards.add(card);
        }
        refreshGridContent();
    }

    // Done    
    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    // Done    
    public void populateInitialGridContent() {
        if (gridPane == null) {
            System.err.println("GridPane is not set.");
            return;
        }
        hostelCards.clear();

        List<Hostel> hostels = student.getHostel();
        for (Hostel hostel : hostels) {
            HostelCard card = new HostelCard(hostel.getHostelId(), hostel.getHostelName(), hostel.getRooms().size(), hostel.getStudents().size());
            card.setOnMouseClicked(e -> cardClicked(hostel.getHostelId()));
            hostelCards.add(card);
        }

        refreshGridContent();
    }

    // Done
    public void refreshGridContent() {
        if (gridPane == null) {
            System.err.println("GridPane is not set.");
            return;
        }

        gridPane.getChildren().clear();
        for (HostelCard card : hostelCards) {
            addBox(card);
            System.out.println("Added card: ");
        }
    }

    // TODO : Implement
    public void cardClicked(String hostelid) {//mysql getHostelById
        System.out.println("Clicked on: " + hostelid);
    }

    // TODO : Implement
    public void onMaintenanceRequestSubmitted(String complaint, String details) { //student onMaintenanceRequestSubmitted
        System.out.println("Maintenance Request Submitted: " + complaint + " - " + details);
    }

    // Done
    public void editProfile(String name, String email, String phone, String password, String password2) {
        System.out.println("Profile Edited: " + name + " - " + email + " - " + phone + " - " + password + " - " + password2);
        if (password.equals(password2))
        {
            student.editProfile(name, email, phone, password);
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
        this.student = student;
    }
    
    // Done
    private void addBox(HostelCard card) {
        if (gridPane == null) {
            System.err.println("GridPane is not set.");
            return;
        }
        
        int cardWidth = card.getCardWidth();
        int columns = gridPane.getWidth() > 0 ? (int) (gridPane.getWidth() / cardWidth) : 1;
        columns = Math.max(1, columns);

        int index = gridPane.getChildren().size();
        int row = index / columns;
        int col = index % columns;

        gridPane.add(card, col, row);
    }

}
