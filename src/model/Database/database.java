package model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Hostel.Complaint;
import model.Hostel.Hostel;
import model.Hostel.MaintenanceRequest;
import model.Hostel.Room;
import model.User.AdministratorModel;
import model.User.MaintenanceStaffModel;
import model.User.StudentModel;
import model.User.UserModel;

public class database {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/hostelmanagementsystem";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "umerghafoor";

    private static Connection connection;

    // Connect to the database
    private static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return connection;
    }
    
    // Disconnect from the database
    private static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            System.err.println("Disconnection failed: " + e.getMessage());
        }
    }
    
    public static UserModel SignUp(String userid,String name,String email,String phone, String password, String type) {
        connect();

        String query = "INSERT INTO USERS (user_id, name, email, phone_number, university_or_job, address, organization_address, userpassword, user_type) " +
                    "VALUES (?, ?, ?, ?, NULL, NULL, NULL, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userid);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, password);
            stmt.setString(6, type);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User signed up successfully!");
                
                // UserModel newUser = new UserModel(userid,name,email,phone);
                UserModel newUser = null;
                if (type.equals("student")) 
                {
                    newUser = new StudentModel(userid, name, email, phone, password, type, query);
                }
                else if (type.equals("maintenance_staff")) 
                {
                    newUser = new MaintenanceStaffModel(userid, name, email, phone, type, query);
                }
                else if (type.equals("administrator")) 
                {
                    newUser = new AdministratorModel(userid, name, email, phone, query);
                }

                disconnect();
                return newUser;
            }
        } catch (SQLException e) {
            System.out.println("Error during sign-up: " + e.getMessage());
        } finally {
            disconnect();
        }

        return null;
    }

    public static UserModel SignIn(String userid, String password) {
        connect();

        String query = "SELECT * FROM USERS WHERE user_id = ? AND userpassword = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userid);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("User signed in successfully!");

                UserModel newUser = null;

                if(resultSet.getString("user_type").equals("student"))
                {
                    newUser = new StudentModel(userid, resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("userpassword"), resultSet.getString("user_type"), query);
                    disconnect();
                    return newUser;
                }
                else if(resultSet.getString("user_type").equals("maintenance_staff"))
                {
                    newUser = new MaintenanceStaffModel(userid, resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("user_type"), query);
                    disconnect();
                    return newUser;
                }
                else if(resultSet.getString("user_type").equals("administrator"))
                {
                    newUser = new AdministratorModel(userid, resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone_number"), query);
                    disconnect();
                    return newUser;
                }
                
                disconnect();
                return newUser;
            }
        } catch (SQLException e) {
            System.out.println("Error during sign-in: " + e.getMessage());
        } finally {
            disconnect();
        }

        return null;
    }

    // SELECT * FROM hostels;
    public static List<Hostel> getHostels() {
        List<Hostel> hostels = new ArrayList<>();
        connect();

        String query = "SELECT * FROM hostels";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                // System.out.println("Hostel ID: " + resultSet.getString("hostel_id"));
                // System.out.println("Hostel Name: " + resultSet.getString("hostel_name"));
                // System.out.println("Hostel Location: " + resultSet.getString("hostel_location"));
                Hostel hostel = new Hostel(resultSet.getString("hostel_id"), resultSet.getString("hostel_name"), resultSet.getString("hostel_location"));
                hostel.setRooms(getRooms(resultSet.getString("hostel_id")));
                hostels.add(hostel);
            }
        } catch (SQLException e) {
            System.out.println("Error during getting hostels: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return hostels;
    }

    // SELECT * FROM hostels WHERE hostel_id = 'H001';
    public static Hostel gHostelbyID(String hostelID) {
        connect();
        Hostel hostel = null;

        String query = "SELECT * FROM hostels WHERE hostel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostelID);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // System.out.println("Hostel ID: " + resultSet.getString("hostel_id"));
                // System.out.println("Hostel Name: " + resultSet.getString("hostel_name"));
                // System.out.println("Hostel Location: " + resultSet.getString("hostel_location"));
                hostel = new Hostel(resultSet.getString("hostel_id"), resultSet.getString("hostel_name"), resultSet.getString("hostel_location"));
                hostel.setRooms(database.getRooms(hostelID));
                return hostel;
            }
        } catch (SQLException e) {
            System.out.println("Error during getting hostel by ID: " + e.getMessage());
        } finally {
            disconnect();
        }        
        
        return null;
    }

    public static Room getRoomByRoomID(String roomId) {
        connect();
        Room room = null;

        String query = "SELECT * FROM rooms WHERE room_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roomId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // Room(String roomId, int maxBeds, int freeSpace, int roomNo)
                room = new Room(resultSet.getString("room_id"), resultSet.getInt("max_beds"), resultSet.getInt("free_space"), resultSet.getInt("room_no"));
                return room;
            }
        } catch (SQLException e) {
            System.out.println("Error during getting room by room ID: " + e.getMessage());
        } finally {
            disconnect();
        }        
        
        return null;
    }
    
    private static List<Room> getRooms(String hostelID) {
        List<Room> rooms = new ArrayList<>();

        String query = "SELECT * FROM rooms WHERE hostel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostelID);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                // Room(String roomId, int maxBeds, int freeSpace, int roomNo)
                Room room = new Room(resultSet.getString("room_id"), resultSet.getInt("max_beds"), resultSet.getInt("free_space"), resultSet.getInt("room_no"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error during getting rooms: " + e.getMessage());
        }
        
        return rooms;
    }

    // SELECT * FROM hostels WHERE hostel_name LIKE '%Hostel 1%';
    public static List<Hostel> getHostels(String hostelName) {
        List<Hostel> hostels = new ArrayList<>();
        connect();

        String query = "SELECT * FROM hostels WHERE hostel_name LIKE ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + hostelName + "%");

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                // System.out.println("Hostel ID: " + resultSet.getString("hostel_id"));
                // System.out.println("Hostel Name: " + resultSet.getString("hostel_name"));
                // System.out.println("Hostel Location: " + resultSet.getString("hostel_location"));
                Hostel hostel = new Hostel(resultSet.getString("hostel_id"), resultSet.getString("hostel_name"), resultSet.getString("hostel_location"));
                hostel.setRooms(getRooms(resultSet.getString("hostel_id")));
                hostels.add(hostel);
            }
        } catch (SQLException e) {
            System.out.println("Error during getting hostels by name: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return hostels;
    }

    // UPDATE USERS
    // SET name = 'Jane Doe' WHERE user_id = 'U001';
    public static void updateName(String userid, String name) {
        connect();

        String query = "UPDATE USERS SET name = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, userid);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Name updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updating name: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public static void updateEmail(String userid, String email) {
        connect();

        String query = "UPDATE USERS SET email = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, userid);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Email updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updating email: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public static void updatePassword(String userid, String password) {
        connect();

        String query = "UPDATE USERS SET userpassword = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, password);
            stmt.setString(2, userid);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updating password: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public static void updatePhone(String userid, String phone) {
        connect();

        String query = "UPDATE USERS SET phone_number = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, phone);
            stmt.setString(2, userid);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Phone updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updating phone: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public static void updateUniversityOrJob(String userid, String universityOrJob) {
        connect();

        String query = "UPDATE USERS SET university_or_job = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, universityOrJob);
            stmt.setString(2, userid);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("University or Job updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updating university or job: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public static void updateAddress(String userid, String address) {
        connect();

        String query = "UPDATE USERS SET address = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, address);
            stmt.setString(2, userid);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Address updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updating address: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public static void updateOrganizationAddress(String userid, String organizationAddress) {
        connect();

        String query = "UPDATE USERS SET organization_address = ? WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, organizationAddress);
            stmt.setString(2, userid);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Organization Address updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error during updating organization address: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    // SELECT submit_room_book_request('umer', 'R001', 'H001');
    public static boolean bookRoomRequwst(String userid, String hostelId, String roomId) {
        connect();

        String query = "SELECT submit_room_book_request(?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userid);
            stmt.setString(2, roomId);
            stmt.setString(3, hostelId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Room booked successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during booking room: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

    // SELECT * FROM USERS WHERE user_id = 'U001';
    public static String getOrganizationAddress(String userId) {
        connect();

        String query = "SELECT organization_address FROM USERS WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("organization_address");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting organization address: " + e.getMessage());
        } finally {
            disconnect();
        }

        return null;
    }

    public static String getUniversityOrJob(String userId) {
        connect();

        String query = "SELECT university_or_job FROM USERS WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("university_or_job");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting university or job: " + e.getMessage());
        } finally {
            disconnect();
        }

        return null;
    }

    public static String getAddress(String userId) {
        connect();

        String query = "SELECT address FROM USERS WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("address");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting address: " + e.getMessage());
        } finally {
            disconnect();
        }

        return null;
    }

    // INSERT INTO user_has_room (user_id, room_id, hostel_id) VALUES
    // ('umer', 'R001', 'H001');
    public static Hostel gHostelbyStudentID(String userId) {
        connect();
        Hostel hostel = null;

        String query = "SELECT * FROM user_has_room WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                hostel = gHostelbyID(resultSet.getString("hostel_id"));
                return hostel;
            }
        } catch (SQLException e) {
            System.out.println("Error during getting hostel by student ID: " + e.getMessage());
        } finally {
            disconnect();
        }        
        
        return null;
    }

    public static Room getRoomByStudentID(String userId) {
        connect();
        Room room = null;

        String query = "SELECT * FROM user_has_room WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                room = getRoomByRoomID(resultSet.getString("room_id"));
                return room;
            }
        } catch (SQLException e) {
            System.out.println("Error during getting room by student ID: " + e.getMessage());
        } finally {
            disconnect();
        }        
        
        return null;
    }

    // SELECT submit_maintenance_request('R001', 'H001', 'umer', 'Leaky faucet');
    public static boolean saveMaintenanceRequest(MaintenanceRequest request) {
        connect();
        String roomId = getRoomIdbyNumber(request.getHostelId(), request.getRoomNo());

        if (roomId == null) {
            System.out.println("Error: Room ID not found for the maintenance request.");
            disconnect();
            return false;
        }

        String query = "SELECT submit_maintenance_request(?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roomId);
            stmt.setString(2, request.getHostelId());
            stmt.setString(3, request.getuserId());
            stmt.setString(4, request.getDescription());

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Maintenance request submitted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during submitting maintenance request: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

    private static String getRoomIdbyNumber(String hostelId,int roomNo) {
        String query = "SELECT room_id FROM rooms WHERE room_no = ? AND hostel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, roomNo);
            stmt.setString(2, hostelId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("room_id");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting room ID by number: " + e.getMessage());
        }

        return null;
    }

    // SELECT submit_complaint('R001', 'H001', 'umer', 'No hot water');
    public static boolean saveComplaint(Complaint complaint) {
        connect();
        String roomId = getRoomIdbyNumber(complaint.getHostelId(), complaint.getRoomNo());

        if (roomId == null) {
            System.out.println("Error: Room ID not found for the complaint.");
            disconnect();
            return false;
        }

        String query = "SELECT submit_complaint(?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roomId);
            stmt.setString(2, complaint.getHostelId());
            stmt.setString(3, complaint.getComplaintId());
            stmt.setString(4, complaint.getDescription());

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Complaint submitted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during submitting complaint: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

}
