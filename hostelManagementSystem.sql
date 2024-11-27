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

INSERT INTO USERS (user_id, name, email, phone_number, university_or_job, address, organization_address, userpassword, user_type) VALUES
('sta', 'John Doe', 'sta@email.com', '1234567890', 'University of XYZ', '123 Main St, City, Country', NULL, 'sta', 'maintenance_staff');


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
SELECT * FROM hostels WHERE hostel_id = 'H001';


-- rooms table

CREATE TABLE rooms (
    room_id VARCHAR(255) PRIMARY KEY,
    hostel_id VARCHAR(255) NOT NULL,
    room_no INT NOT NULL,
    max_beds INT NOT NULL,
    free_space INT NOT NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);

INSERT INTO rooms (room_id, hostel_id, room_no, max_beds, free_space) VALUES
('R001', 'H001', 101, 2, 1);

INSERT INTO rooms (room_id, hostel_id, room_no, max_beds, free_space) VALUES
('R003', 'H002', 101, 5, 2);

SELECT * FROM rooms;

UPDATE rooms
SET free_space = 0 WHERE room_id = 'R001' AND hostel_id = 'H001';

--  Add new room with uinque Room ID
DELIMITER $$
CREATE FUNCTION add_new_room (
    hostel_id_param VARCHAR(255),
    room_no_param INT,
    max_beds_param INT
) RETURNS BOOLEAN
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE is_added BOOLEAN;
    DECLARE new_room_id VARCHAR(255);

    -- Generate a unique room_id
    SET new_room_id = CONCAT('R', UUID());

    -- Check if room already EXISTS
    IF EXISTS (SELECT 1 FROM rooms WHERE room_no = room_no_param AND hostel_id = hostel_id_param) THEN
        SET is_added = FALSE;
    ELSE
        -- Insert the new room into the rooms table
        INSERT INTO rooms (room_id, hostel_id, room_no, max_beds, free_space)
        VALUES (new_room_id, hostel_id_param, room_no_param, max_beds_param, max_beds_param);

        -- Determine if the room was added
        SET is_added = ROW_COUNT() > 0;
    END IF;

    -- Determine if the room was added
    SET is_added = ROW_COUNT() > 0;

    RETURN is_added;
END$$


DELIMITER ;

SELECT add_new_room('H001', 102, 3);

--  submit room book request{ "Alice", "001", "Hostel A", "Room 101" }) autoassign request_id

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


DELIMITER $$

CREATE FUNCTION submit_room_book_request (
    user_id VARCHAR(255),
    room_id VARCHAR(255),
    hostel_id VARCHAR(255)
) RETURNS VARCHAR(255)
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE generated_request_id VARCHAR(255);
    
    -- Generate a unique request_id
    SET generated_request_id = UUID();

    -- Insert the booking request into the room_book_requests table
    INSERT INTO room_book_requests (
        request_id, user_id, room_id, hostel_id, status
    ) VALUES (
        generated_request_id, user_id, room_id, hostel_id, 'Pending'
    );

    -- Return the generated request_id
    RETURN generated_request_id;
END$$

DELIMITER ;


SELECT submit_room_book_request('umer', 'R001', 'H001');

SELECT * FROM room_book_requests;

DELETE FROM room_book_requests WHERE request_id = '9349c71e-aa4c-11ef-b440-482ae32943bf';


-- student Room booking
CREATE TABLE user_has_room (
    user_id VARCHAR(255) NOT NULL,
    room_id VARCHAR(255) NOT NULL,
    hostel_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);

INSERT INTO user_has_room (user_id, room_id, hostel_id) VALUES
('umer', 'R001', 'H001');

SELECT * FROM user_has_room;

DELETE FROM user_has_room WHERE user_id = 'umer';


-- maintenance_requests TODO

CREATE TABLE maintenance_requests (
    request_id VARCHAR(255) PRIMARY KEY,
    room_id VARCHAR(255) NOT NULL,
    hostel_id VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('Pending', 'In Progress', 'Completed') DEFAULT 'Pending',
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);

INSERT INTO maintenance_requests (request_id, room_id, hostel_id, description, status) VALUES
('R001', 'R001', 'H001', 'Leaky faucet', 'Pending');

DELIMITER $$

CREATE FUNCTION submit_maintenance_request (
    room_id VARCHAR(255),
    hostel_id VARCHAR(255),
    user_id VARCHAR(255),
    description TEXT
) RETURNS VARCHAR(255)
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE generated_request_id VARCHAR(255);
    
    -- Generate a unique request_id
    SET generated_request_id = UUID();

    -- Insert the maintenance request into the maintenance_requests table
    INSERT INTO maintenance_requests (
        request_id, room_id, hostel_id, description, status
    ) VALUES (
        generated_request_id, room_id, hostel_id, description, 'Pending'
    );

    -- Return the generated request_id
    RETURN generated_request_id;
END$$


DELIMITER ;

SELECT submit_maintenance_request('R001', 'H001', 'umer', 'Leaky faucet');


SELECT * FROM maintenance_requests;
SELECT * FROM complaints;

-- complaints TODO

CREATE TABLE complaints (
    complaint_id VARCHAR(255) PRIMARY KEY,
    room_id VARCHAR(255),
    hostel_id VARCHAR(255),
    description TEXT NOT NULL,
    status ENUM('Pending', 'Resolved') DEFAULT 'Pending',
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);


INSERT INTO complaints (complaint_id, room_id, hostel_id, description, status) VALUES
('C001', 'R001', 'H001', 'No hot water', 'Pending');

DELIMITER $$
CREATE FUNCTION submit_complaint (
    room_id VARCHAR(255),
    hostel_id VARCHAR(255),
    user_id VARCHAR(255),
    description TEXT
) RETURNS VARCHAR(255)
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE generated_complaint_id VARCHAR(255);
    
    -- Generate a unique complaint_id
    SET generated_complaint_id = UUID();

    -- Insert the complaint into the complaints table
    INSERT INTO complaints (
        complaint_id, room_id, hostel_id, description, status
    ) VALUES (
        generated_complaint_id, room_id, hostel_id, description, 'Pending'
    );

    -- Return the generated complaint_id
    RETURN generated_complaint_id;
END$$

SELECT submit_complaint('R001', 'H001', 'umer', 'No hot water');

SELECT * FROM complaints;


-- Admin owns hostel table

CREATE TABLE admin_owns_hostel (
    admin_id VARCHAR(255) NOT NULL,
    hostel_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE
);

INSERT INTO admin_owns_hostel (admin_id, hostel_id) VALUES
('mine', 'H001');

-- drop data
DELETE FROM admin_owns_hostel WHERE admin_id = 'umer';

SELECT * FROM admin_owns_hostel;


DELIMITER $$

CREATE FUNCTION aprove_room_book_request (
    request_id VARCHAR(255)
) RETURNS BOOLEAN
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE is_approved BOOLEAN;

    UPDATE room_book_requests
    SET status = 'Approved'
    WHERE request_id = request_id;

    INSERT INTO user_has_room (user_id, room_id, hostel_id)
    SELECT user_id, room_id, hostel_id FROM room_book_requests WHERE request_id = request_id;

    SET is_approved = ROW_COUNT() > 0;

    RETURN is_approved;
END$$

DELIMITER ;

SELECT aprove_room_book_request('9349c71e-aa4c-11ef-b440-482ae32943bf');

SELECT * FROM room_book_requests;

-- Admin can reject room booking request

DELIMITER $$

CREATE FUNCTION reject_room_book_request (
    request_id VARCHAR(255)
) RETURNS BOOLEAN
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE is_rejected BOOLEAN;

    UPDATE room_book_requests
    SET status = 'Rejected'
    WHERE request_id = request_id;

    SET is_rejected = ROW_COUNT() > 0;
    
    RETURN is_rejected;
END$$

DELIMITER ;

SELECT reject_room_book_request('9349c71e-aa4c-11ef-b440-482ae32943bf');

SELECT * FROM room_book_requests;

-- Admin remove Student from Hostel Room

DELIMITER $$
CREATE FUNCTION remove_student_from_room (
    user_id VARCHAR(255)
) RETURNS BOOLEAN
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE is_removed BOOLEAN;

    DELETE FROM user_has_room
    WHERE user_id = user_id;

    SET is_removed = ROW_COUNT() > 0;

    RETURN is_removed;
END$$

DELIMITER ;

SELECT remove_student_from_room('umer');

SELECT * FROM user_has_room;

DELETE FROM user_has_room
WHERE user_id = 'umer';

INSERT INTO user_has_room (user_id, room_id, hostel_id) VALUES
('umer', 'R001', 'H001'),
('umer2', 'R002', 'H001');


-- table hostel has Staff

CREATE TABLE hostel_has_staff (
    hostel_id VARCHAR(255) NOT NULL,
    staff_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(hostel_id) ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES users(user_id) ON DELETE CASCADE
);

INSERT INTO hostel_has_staff (hostel_id, staff_id) VALUES
('H001', 'staff');

SELECT * FROM hostel_has_staff;
SELECT * FROM hostel_has_staff WHERE hostel_id = 'H001';

DELETE FROM hostel_has_staff WHERE staff_id = 'staff1';

-- Admin can assign staff to hostel

DELIMITER $$
CREATE FUNCTION assign_staff_to_hostel (
    staff_id VARCHAR(255),
    hostel_id VARCHAR(255),
    email VARCHAR(255)
) RETURNS BOOLEAN
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE is_assigned BOOLEAN;

    -- if staff_id and email matches
    IF EXISTS (SELECT * FROM users WHERE user_id = staff_id AND email = email) THEN
        INSERT INTO hostel_has_staff (hostel_id, staff_id)
        VALUES (hostel_id, staff_id);

        SET is_assigned = ROW_COUNT() > 0;
    ELSE
        SET is_assigned = FALSE;
    END IF;

    SET is_assigned = ROW_COUNT() > 0;

    RETURN is_assigned;
END$$

DELIMITER ;



SELECT assign_staff_to_hostel('staff', 'H001', 'email');

SELECT * FROM hostel_has_staff;

DELETE FROM hostel_has_staff WHERE staff_id = 'staff';



-- add new hostel INSERT INTO hostels (hostel_id, hostel_name, hostel_location) VALUES (?, ?, ?)

DELIMITER $$
CREATE FUNCTION add_new_hostel (
    owner_id VARCHAR(255),
    hostel_id VARCHAR(255),
    hostel_name VARCHAR(255),
    hostel_location VARCHAR(255)
) RETURNS BOOLEAN
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE is_added BOOLEAN;

    -- Check if the hostel_id already exists
    IF EXISTS (SELECT 1 FROM hostels h WHERE h.hostel_id = hostel_id) THEN
        SET is_added = FALSE;
    ELSE
        -- Add new hostel
        INSERT INTO hostels (hostel_id, hostel_name, hostel_location)
        VALUES (hostel_id, hostel_name, hostel_location);

        -- Link admin with the hostel
        INSERT INTO admin_owns_hostel (admin_id, hostel_id)
        VALUES (owner_id, hostel_id);

        -- Confirm success
        SET is_added = TRUE;
    END IF;

    RETURN is_added;
END$$
DELIMITER ;

-- DROP OLDER
DROP FUNCTION add_new_hostel;

SELECT add_new_hostel('igabdullah', 'bob', 'Bob Hostel', 'F11, Islamabad');

SELECT * FROM hostels;

-- Admin Removes Hostel
DELIMITER $$

CREATE FUNCTION remove_hostel (
    owner_id VARCHAR(255),
    hostel_id_param VARCHAR(255) -- Renamed parameter for clarity
) RETURNS BOOLEAN
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE is_removed BOOLEAN;

    -- Check if the owner is an admin of the hostel
    IF EXISTS (
        SELECT 1
        FROM admin_owns_hostel
        WHERE admin_id = owner_id AND hostel_id = hostel_id_param
    ) THEN
        -- Remove dependent rows
        DELETE FROM user_has_room WHERE hostel_id = hostel_id_param;
        DELETE FROM room_book_requests WHERE hostel_id = hostel_id_param;
        DELETE FROM maintenance_requests WHERE hostel_id = hostel_id_param;
        DELETE FROM complaints WHERE hostel_id = hostel_id_param;
        DELETE FROM hostel_has_staff WHERE hostel_id = hostel_id_param;

        -- Remove the hostel and admin ownership record
        DELETE FROM hostels WHERE hostel_id = hostel_id_param;
        DELETE FROM admin_owns_hostel WHERE hostel_id = hostel_id_param;

        -- Determine if the hostel was removed
        SET is_removed = (ROW_COUNT() > 0);
    ELSE
        -- Owner is not authorized
        SET is_removed = FALSE;
    END IF;

    RETURN is_removed;
END$$

DELIMITER ;

DROP FUNCTION remove_hostel;

SELECT remove_hostel('mine', 'goldenera');

SELECT * FROM hostels;


-- Set maintenance rewuest to Completed or In Progress

UPDATE maintenance_requests
SET status = 'Completed'
WHERE request_id = 'R001';

SELECT * FROM maintenance_requests;
