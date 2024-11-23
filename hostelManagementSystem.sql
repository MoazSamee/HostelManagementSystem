create database hostelmanagementsystem;
use hostelmanagementsystem;

CREATE TABLE hostels (
    hostel_id VARCHAR(255) PRIMARY KEY,
    hostel_name VARCHAR(255) NOT NULL
);

INSERT INTO hostels (hostel_id, hostel_name, hostel_location) VALUES
('H005', 'Hostel 5','H13');

SELECT * FROM hostels;

ALTER TABLE hostels
ADD hostel_location VARCHAR(255) NOT NULL;

CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    hostel_id VARCHAR(255),
    user_type ENUM('student', 'maintenance_staff', 'administrator') NOT NULL,
    address TEXT,
    university_or_job VARCHAR(255),
    organization_address TEXT,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE SET NULL
);

CREATE TABLE rooms (
    room_id VARCHAR(255) PRIMARY KEY,
    hostel_id VARCHAR(255) NOT NULL,
    room_no INT NOT NULL,
    max_beds INT NOT NULL,
    free_space INT NOT NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);
CREATE TABLE packages (
    package_id VARCHAR(255) PRIMARY KEY,
    daily_charges DECIMAL(10, 2) NOT NULL,
    weekly_charges DECIMAL(10, 2) NOT NULL,
    monthly_charges DECIMAL(10, 2) NOT NULL,
    room_type VARCHAR(255) NOT NULL,
	hostel_id VARCHAR(255) NOT NULL,
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
    complaint_id VARCHAR(255) PRIMARY KEY, -- Unique identifier for the complaint
    room_id VARCHAR(255),                 -- ID of the room where the complaint originated
    hostel_id VARCHAR(255),               -- ID of the hostel
    description TEXT NOT NULL,            -- Description of the complaint issue
    status ENUM('Pending', 'Resolved') DEFAULT 'Pending', -- Status of the complaint
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);


------------------------------------------------------------------------------------------------
---                              MY SQL QUERIES                                              ---
------------------------------------------------------------------------------------------------

create database hostelmanagementsystem;
drop database hostelmanagementsystem;
use hostelmanagementsystem;

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

SELECT * FROM USERS;

INSERT INTO USERS (user_id, name, email, phone_number, university_or_job, address, organization_address, userpassword, user_type) VALUES
('U001', 'John Doe', 'example@email.com', '1234567890', 'University of XYZ', '123 Main St, City, Country', NULL, 'password123', 'student');

SELECT * FROM USERS WHERE user_id = 'U001';

-- update user name email password phone_number university_or_job address organization_address
UPDATE USERS
SET name = 'Jane Doe' WHERE user_id = 'U001';

UPDATE USERS
SET email = 'example' WHERE user_id = 'U001';

UPDATE USERS
SET userpassword = 'password' WHERE user_id = 'U001';

UPDATE USERS
SET phone_number = '0987654321' WHERE user_id = 'U001';

UPDATE USERS
SET university_or_job = 'University of ABC' WHERE user_id = 'U001';

UPDATE USERS
SET address = '456 Main St, City, Country' WHERE user_id = 'U001';

UPDATE USERS
SET organization_address = '789 Main St, City, Country' WHERE user_id = 'U001';

-- hostel table

CREATE TABLE hostels (
    hostel_id VARCHAR(255) PRIMARY KEY,
    hostel_name VARCHAR(255) NOT NULL,
    hostel_location VARCHAR(255) NOT NULL
);

INSERT INTO hostels (hostel_id, hostel_name, hostel_location) VALUES
('H001', 'Hostel 1', 'Location 1');

SELECT * FROM hostels;

SELECT * FROM hostels WHERE hostel_name LIKE '%Hostel 1%';


