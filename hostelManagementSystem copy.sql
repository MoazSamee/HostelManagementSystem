------------------------------------------------------------------------------------------------
---                              MY SQL QUERIES                                              ---
------------------------------------------------------------------------------------------------

CREATE TABLE USERS (
    user_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    university_or_job VARCHAR(255),
    address TEXT,
    organization_address TEXT,
    userpassword VARCHAR(255) NOT NULL,
    user_type ENUM('student', 'maintenance_staff', 'administrator') NOT NULL
);
CREATE TABLE hostels (
    hostel_id VARCHAR(255) PRIMARY KEY,
    hostel_name VARCHAR(255) NOT NULL,
    hostel_location VARCHAR(255) NOT NULL
);
CREATE TABLE rooms (
    room_id VARCHAR(255) PRIMARY KEY,
    hostel_id VARCHAR(255) NOT NULL,
    room_no INT NOT NULL,
    max_beds INT NOT NULL,
    free_space INT NOT NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);
CREATE TABLE room_book_requests (
    request_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    room_id VARCHAR(255) NOT NULL,
    hostel_id VARCHAR(255) NOT NULL,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);
CREATE TABLE user_has_room (
    user_id VARCHAR(255) NOT NULL,
    room_id VARCHAR(255) NOT NULL,
    hostel_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);
CREATE TABLE maintenance_requests (
    request_id VARCHAR(255) PRIMARY KEY,
    room_id VARCHAR(255) NOT NULL,
    hostel_id VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('Pending', 'In Progress', 'Completed') DEFAULT 'Pending',
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);

CREATE TABLE complaints (
    complaint_id VARCHAR(255) PRIMARY KEY,
    room_id VARCHAR(255),
    hostel_id VARCHAR(255),
    description TEXT NOT NULL,
    status ENUM('Pending', 'Resolved') DEFAULT 'Pending',
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);
CREATE TABLE admin_owns_hostel (
    admin_id VARCHAR(255) NOT NULL,
    hostel_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);
CREATE TABLE hostel_has_staff (
    hostel_id VARCHAR(255) NOT NULL,
    staff_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES users(user_id) ON DELETE CASCADE
);
