package model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Hostel.Hostel;
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
                hostels.add(hostel);
            }
        } catch (SQLException e) {
            System.out.println("Error during getting hostels: " + e.getMessage());
        } finally {
            disconnect();
        }
        
        return hostels;
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


}
