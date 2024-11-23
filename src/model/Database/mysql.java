package model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Hostel.Hostel;
import model.Hostel.MaintenanceRequestModel;

public class mysql {
    private final String url = "jdbc:mysql://localhost:3306/hostelmanagementsystem";
    private final String username = "root";
    private final String password = "232323";

    // Function to fetch all hostels
    public List<Hostel> fetchAllHostels() {
        List<Hostel> hostels = new ArrayList<>();
        String query = "SELECT * FROM hostels";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String hostelId = resultSet.getString("hostel_id");
                String hostelName = resultSet.getString("hostel_name");
                String hostelLocation = resultSet.getString("hostel_location");

                hostels.add(new Hostel(hostelId, hostelName, hostelLocation));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hostels;
    }
    
    public Hostel getHostelByName(String hostelName) {
        String query = "SELECT * FROM hostels WHERE hostel_name = ?";
        Hostel hostel = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, hostelName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hostelId = resultSet.getString("hostel_id");
                String hostelLocation = resultSet.getString("hostel_location");

                hostel = new Hostel(hostelId, hostelName, hostelLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hostel;
    }
    
    public Hostel getHostelById(String hostelId) {
        String query = "SELECT * FROM hostels WHERE hostel_id = ?";
        Hostel hostel = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, hostelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hostelName = resultSet.getString("hostel_name");
                String hostelLocation = resultSet.getString("hostel_location");

                hostel = new Hostel(hostelId, hostelName, hostelLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hostel;
    }

    public boolean saveMaintenanceRequest(MaintenanceRequestModel request) {
    String query = "INSERT INTO maintenance_requests (request_id, room_no, hostel_id, description, status) " +
                   "VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, request.getRequestId());
        preparedStatement.setString(2, String.valueOf( request.getRoomNo()));
        preparedStatement.setString(3, request.getHostelId());
        preparedStatement.setString(4, request.getDescription());
        preparedStatement.setString(5, request.getStatus());

        return preparedStatement.executeUpdate() > 0; // Returns true if the row is inserted
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
  }


}
