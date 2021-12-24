-- SET GLOBAL interactive_timeout=60;
CREATE SCHEMA IF NOT EXISTS HotelSchema;
USE HotelSchema;
-- select * from HotelSchema.guest;
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS guest;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS numbers;
DROP TABLE IF EXISTS hotel;
CREATE TABLE guest(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	surname VARCHAR(20) NOT NULL,
    nationality VARCHAR(20),
	gender VARCHAR(20),
	birthDate DATE NOT NULL,
    email VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    discount INT DEFAULT 0
);
CREATE TABLE hotel(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	address VARCHAR(50) NOT NULL,
	website VARCHAR(20),
	stars INT
);
CREATE TABLE numbers(
	number VARCHAR(20) PRIMARY KEY,
    hotelId INT NOT NULL,
    FOREIGN KEY(hotelId) REFERENCES hotel(id) ON DELETE CASCADE
);
CREATE TABLE room(
	number VARCHAR(20) PRIMARY KEY,
    hotelId INT NOT NULL,
    category VARCHAR(20) NOT NULL,
    roomType VARCHAR(20) NOT NULL,
    roomView VARCHAR(10) NOT NULL,
    imgName VARCHAR(20) NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY(hotelId) REFERENCES hotel(id) ON DELETE CASCADE
);
CREATE TABLE booking(
	id INT AUTO_INCREMENT PRIMARY KEY,
    hotelId INT NOT NULL,
    guestId INT NOT NULL,
    checkIn DATE NOT NULL,
    checkOut DATE NOT NULL,
    roomNumber VARCHAR(20) NOT NULL,
    reservationPrice DOUBLE NOT NULL,
    FOREIGN KEY(hotelId) REFERENCES hotel(id) ON DELETE CASCADE,
     FOREIGN KEY(roomNumber) REFERENCES room(number) ON DELETE CASCADE,
      FOREIGN KEY(guestId) REFERENCES guest(id) ON DELETE CASCADE
);

INSERT INTO hotel(name, address,website,stars) 
VALUES('Chicago Histon','610 E 89th Pl Chicago, Illinois(IL), 60619','chicagohiston.com',5);

INSERT INTO numbers VALUES('1873622888',1);
INSERT INTO numbers VALUES('1999888777',1);
INSERT INTO numbers VALUES('1223334555',1);
-- select * from room;
INSERT INTO room VALUES('1A',1,'single','standard','mountains','1A.jpeg',20);
INSERT INTO room VALUES('1B',1,'single','standard','sea','1B.jpeg',23);
INSERT INTO room VALUES('1C',1,'single','standard','sea','1C.jpeg',35);
INSERT INTO room VALUES('1D',1,'single','standard','mountains','1D.jpeg',30);
INSERT INTO room VALUES('2D',1,'single','standard','mountains','2D.jpeg',25);
INSERT INTO room VALUES('2A',1,'single','standard','mountains','2A.jpeg',40);
INSERT INTO room VALUES('2B',1,'single','standard','mountains','2B.jpeg',25);

INSERT INTO room VALUES('3A',1,'double','standard','sea','3A.jpeg',25);
INSERT INTO room VALUES('3B',1,'double','standard','sea','3B.jpeg',40);
INSERT INTO room VALUES('3C',1,'single','apartment','sea','3C.jpeg',50);
INSERT INTO room VALUES('3D',1,'double','apartment','sea','3D.jpeg',20);

INSERT INTO room VALUES('4A',1,'double','apartment','sea','4A.jpeg',30);
INSERT INTO room VALUES('4B',1,'double','apartment','sea','4B.jpeg',30);
INSERT INTO room VALUES('4C',1,'single','standard','sea','4C.jpeg',20);
INSERT INTO room VALUES('4D',1,'single','standard','sea','4D.jpeg',25);

-- INSERT INTO booking(hotelId,guestId,checkIn,checkOut,roomNumber) 
-- VALUES(1,7,STR_TO_DATE('01/05/2021','%d/%m/%Y'),STR_TO_DATE('05/05/2021','%d/%m/%Y'),'1A');
-- INSERT INTO booking(hotelId,guestId,checkIn,checkOut,roomNumber) 
-- VALUES(1,7,STR_TO_DATE('14/05/2021','%d/%m/%Y'),STR_TO_DATE('15/05/2021','%d/%m/%Y'),'1D');


DELIMITER //

CREATE  PROCEDURE InsertBooking(
	IN hotel int,
    IN guest int,
    IN checkin VARCHAR(20),
    IN checkout VARCHAR(20),
	IN roomnum VARCHAR(20))
BEGIN
	DECLARE priceRoom DOUBLE;
    DECLARE days INT;
    DECLARE resPrice DOUBLE;
    DECLARE bookingsNum INT;
    DECLARE clientDiscount INT;
	SELECT price INTO priceRoom FROM room WHERE number=roomnum;
    SET days = STR_TO_DATE(checkout,'%Y-%m-%d')-STR_TO_DATE(checkin,'%Y-%m-%d');
    
	
	SELECT COUNT(b.id) bookings,discount INTO bookingsNum,clientDiscount 
    FROM booking b 
    JOIN guest g ON g.id=b.guestId
    WHERE g.id = guest;
    IF bookingsNum>=3
    THEN UPDATE guest g SET discount=5 WHERE g.Id = guest;
    END IF;
    IF clientDiscount>0
    THEN SET resPrice = days*(priceRoom-(priceRoom*(clientDiscount/100))); -- 20-(20*(5/100))
    ELSE  
     SET resPrice = priceRoom*days;
    END IF;
     
	INSERT INTO booking(hotelId,guestId,checkIn,checkOut,reservationPrice,roomNumber) 
VALUES(hotel,guest,STR_TO_DATE(checkin,'%Y-%m-%d'),STR_TO_DATE(checkout,'%Y-%m-%d'),resPrice,roomnum);
END //

DELIMITER ;

-- DROP PROCEDURE InsertBooking;

DELIMITER //
CREATE TRIGGER checkDiscount   
    AFTER DELETE
         ON booking FOR EACH ROW    
 BEGIN    
        DECLARE bookingNum integer;
        SELECT COUNT(*) INTO bookingNum FROM Booking b WHERE b.guestId = old.guestId; 
        IF bookingNum<3
		THEN 
        UPDATE guest SET discount=0 WHERE id=old.guestId;
        END IF;
	
END//

DELIMITER ;
-- DROP trigger checkDiscount;

-- SELECT * FROM room;
-- CREATE TABLE roomTest (
-- 	id INT PRIMARY KEY,
-- 	roomType VARCHAR(10)
-- );
-- ALTER TABLE roomTest
-- MODIFY COLUMN id VARCHAR(20);

-- INSERT INTO roomTest
-- SELECT number, roomType 
-- FROM room
-- WHERE roomView='sea';

-- SELECT * FROM roomTest;
-- select now();
-- CREATE TABLE purchases (
-- 	id INT PRIMARY KEY AUTO_INCREMENT,
-- 	purchaseDate DATE
-- );
-- INSERT INTO purchases(purchaseDate)
-- VALUES(STR_TO_DATE('2000-01-01','%Y-%m-%d'));
-- INSERT INTO purchases(purchaseDate)
-- VALUES(STR_TO_DATE('2021-07-01','%Y-%m-%d'));

-- SELECT * FROM purchases;
-- SELECT * FROM purchases WHERE purchaseDate >= DATE_ADD(NOW(),INTERVAL -30 DAY);

-- SELECT curdate();