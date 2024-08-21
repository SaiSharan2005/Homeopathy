DELIMITER //

CREATE TRIGGER after_doctor_details_insert
AFTER INSERT ON doctor_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('New doctor added: ID = ', NEW.id), NOW(), 'Doctor', NEW.id);
END//

DELIMITER ;

DROP TRIGGER IF EXISTS after_doctor_details_insert;


DELIMITER //

CREATE TRIGGER after_doctor_details_update
AFTER UPDATE ON doctor_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Doctor updated: ID = ', NEW.id), NOW(), 'Doctor', NEW.id);
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER after_patient_details_insert
AFTER INSERT ON patient_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('New patient added: ID = ', NEW.id), NOW(), 'Patient', NEW.patient_register_id);
END//

DELIMITER ;



DELIMITER //

CREATE TRIGGER after_patient_details_update
AFTER UPDATE ON patient_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Patient updated: ID = ', NEW.id), NOW(), 'Patient', NEW.patient_register_id);
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER after_doctor_details_delete
AFTER DELETE ON doctor_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Doctor deleted: ID = ', OLD.id), NOW(), 'Doctor', OLD.id);
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_patient_details_delete
AFTER DELETE ON patient_details
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (message, timestamp, user_type, user_id)
    VALUES (CONCAT('Patient deleted: ID = ', OLD.id), NOW(), 'Patient', OLD.patient_register_id);
END//

DELIMITER ;


DELIMITER $$

CREATE TRIGGER after_booking_appointment_insert
AFTER INSERT ON booking_appointment
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (user_type, user_id, message, timestamp)
    VALUES ('Appointment', NEW.booking_id, CONCAT('New booking created with Booking ID: ', NEW.booking_id), NOW());
END $$

DELIMITER ;



DELIMITER $$

CREATE TRIGGER after_booking_appointment_update
AFTER UPDATE ON booking_appointment
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (user_type, user_id, message, timestamp)
    VALUES ('Appointment', NEW.booking_id, CONCAT('Booking ID ', NEW.booking_id, ' was updated'), NOW());
END $$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER after_booking_appointment_delete
AFTER DELETE ON booking_appointment
FOR EACH ROW
BEGIN
    INSERT INTO activity_log (user_type, user_id, message, timestamp)
    VALUES ('Appointment', OLD.booking_id, CONCAT('Booking ID ', OLD.booking_id, ' was deleted'), NOW());
END $$

DELIMITER ;

