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
    
    public static void SignUp(String userid,String name,String email,String phone, String password, String type) {
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
                disconnect();
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error during sign-up: " + e.getMessage());
        } finally {
            disconnect();
        }

        return;
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
                    newUser = new MaintenanceStaffModel(userid, resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("address"));
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


    // SELECT * FROM admin_owns_hostel;
    public static List<Hostel> getHostelsbyAdminId(String userId) {
        List<Hostel> hostels = new ArrayList<>();
        connect();

        String query = "SELECT * FROM admin_owns_hostel WHERE admin_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            List<String> hostelIds = new ArrayList<>();
            while (resultSet.next()) {
                hostelIds.add(resultSet.getString("hostel_id"));
            }
            for (String hostelId : hostelIds) {
                Hostel hostel = gHostelbyID(hostelId);
                hostels.add(hostel);
            }
        } catch (SQLException e) {
            System.out.println("Error during getting hostels by admin ID: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return hostels;
    }

    // { "User name", "user phone", "Hostel Name", "Room number" })
    // SELECT * FROM room_book_requests;
    // SELECT * FROM USERS;
    // SELECT * FROM rooms;
    // SELECT * FROM hostels;
    public static List<String[]> getRoomRequests(String hostelId) {
        connect();
        List<String[]> roomRequests = new ArrayList<>();

        String query = "SELECT * FROM room_book_requests WHERE hostel_id = ? AND status = 'pending'";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostelId);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String roomId = resultSet.getString("room_id");
                String roomNo = getRoomNobyId(roomId);
                String userName = getUserNamebyId(userId);
                String userPhone = getUserPhonebyId(userId);
                String requestId = resultSet.getString("request_id");
                roomRequests.add(new String[] { userName, userPhone, roomNo, requestId});
            }
        } catch (SQLException e) {
            System.out.println("Error during getting room requests: " + e.getMessage());
        } finally {
            disconnect();
        }
        return roomRequests;
    }

    private static String getRoomNobyId(String roomId) {
        String query = "SELECT room_no FROM rooms WHERE room_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, roomId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("room_no");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting room number by ID: " + e.getMessage());
        }

        return null;
    }

    private static String getUserNamebyId(String userId) {
        String query = "SELECT name FROM USERS WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting user name by ID: " + e.getMessage());
        }

        return null;
    }

    private static String getUserPhonebyId(String userId) {
        String query = "SELECT phone_number FROM USERS WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("phone_number");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting user phone by ID: " + e.getMessage());
        }

        return null;
    }

    private static String getHostelNamebyId(String hostelId) {
        String query = "SELECT hostel_name FROM hostels WHERE hostel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostelId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("hostel_name");
            }
        } catch (SQLException e) {
            System.out.println("Error during getting hostel name by ID: " + e.getMessage());
        }

        return null;
    }

    // SELECT aprove_room_book_request('9349c71e-aa4c-11ef-b440-482ae32943bf');
    public static boolean approveRoomRequest(String requestID) {
        connect();

        String query = "SELECT aprove_room_book_request(?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, requestID);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Room request approved successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during approving room request: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

    // SELECT reject_room_book_request('9349c71e-aa4c-11ef-b440-482ae32943bf');
    public static boolean disapproveRoomRequest(String requestID) {
        connect();

        String query = "SELECT reject_room_book_request(?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, requestID);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Room request disapproved successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during disapproving room request: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

    // SELECT * FROM USERS WHERE user_type = 'student';
    // SELECT * FROM user_has_room;
    public static List<StudentModel> getStudentsByHostel(String hostelId) {
        List<StudentModel> students = new ArrayList<>();
        connect();

        String query = "SELECT * FROM user_has_room WHERE hostel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostelId);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                StudentModel student = getStudentbyId(userId);
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error during getting students by hostel: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return students;

    }

    private static StudentModel getStudentbyId(String userId) {
        String query = "SELECT * FROM USERS WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // String userId, String name, String email, String phoneNumber, String address, String universityOrJob, String organizationAddress)
                return new StudentModel(userId, resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("address"), resultSet.getString("university_or_job"), resultSet.getString("organization_address"));
            }
        } catch (SQLException e) {
            System.out.println("Error during getting student by ID: " + e.getMessage());
        }

        return null;
    }

    // DELETE FROM user_has_room WHERE user_id = 'umer';
    public static boolean removeStudent(String studentId) {
        connect();

        String query = "DELETE FROM user_has_room WHERE user_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, studentId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student removed successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during removing student: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

    public static List<MaintenanceStaffModel> getMaintenanceStaffByHostel(String hostelId) {
        List<MaintenanceStaffModel> staffs = new ArrayList<>();
        connect();

        String query = "SELECT * FROM hostel_has_staff WHERE hostel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, hostelId);
            
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                MaintenanceStaffModel staff = getMaintenanceStaffbyId(resultSet.getString("staff_id"));
                staffs.add(staff);
            }
        } catch (SQLException e) {
            System.out.println("Error during getting maintenance staff by hostel: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return staffs;
    }

    private static MaintenanceStaffModel getMaintenanceStaffbyId(String userId) {
        String query = "SELECT * FROM USERS WHERE user_id = ? AND user_type = 'maintenance_staff'";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // String staffId, String name, String email, String phoneNumber, String address, String organizationAddress)
                return new MaintenanceStaffModel(userId, resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println("Error during getting maintenance staff by ID: " + e.getMessage());
        }

        return null;
    }

    // SELECT remove_hostel('mine', 'H001');
    public static boolean removeHostel(String hostelId,String userId) {
        connect();

        System.out.println("Removing hostel with ID: " + hostelId);
        String query = "SELECT remove_hostel(?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, hostelId);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // System.out.println("Hostel removed successfully!" + resultSet.getString(0));
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during removing hostel: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

    // SELECT assign_staff_to_hostel('staff', 'H001', 'email');
    public static boolean addStaffToHostel(String hostelId, String username2, String email) {
        connect();

        String query = "SELECT assign_staff_to_hostel(?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username2);
            stmt.setString(2, hostelId);
            stmt.setString(3, email);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Staff added to hostel successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during adding staff to hostel: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

    // DELETE FROM hostel_has_staff WHERE staff_id = 'staff';
    public static boolean removeStaffFromHostel(String hostelId, String id) {
        connect();

        String query = "DELETE FROM hostel_has_staff WHERE staff_id = ? AND hostel_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, hostelId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Staff removed from hostel successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during removing staff from hostel: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }


    // SELECT add_new_hostel('mine', 'H002', 'Hostel 2', 'Location 2');
    public static boolean addHostel(String hostelId, String hostelName, String hostelLocation, String userId) {
        connect();

        String query = "SELECT add_new_hostel(?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, userId);
            stmt.setString(2, hostelId);
            stmt.setString(3, hostelName);
            stmt.setString(4, hostelLocation);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Hostel added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error during adding hostel: " + e.getMessage());
        } finally {
            disconnect();
        }

        return false;
    }

}
