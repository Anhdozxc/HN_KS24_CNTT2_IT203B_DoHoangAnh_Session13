CREATE DATABASE IF NOT EXISTS Hospital_Bai1;
USE Hospital_Bai1;

DROP TABLE IF EXISTS Medicine_Inventory;
DROP TABLE IF EXISTS Prescription_History;

CREATE TABLE Medicine_Inventory (
    medicine_id INT PRIMARY KEY,
    quantity INT
);

CREATE TABLE Prescription_History (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    medicine_id INT,
    date DATETIME
);

INSERT INTO Medicine_Inventory VALUES (1, 10);