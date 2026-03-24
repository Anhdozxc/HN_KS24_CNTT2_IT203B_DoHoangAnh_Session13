CREATE DATABASE IF NOT EXISTS Hospital_Bai3;
USE Hospital_Bai3;

DROP TABLE IF EXISTS Patients;
DROP TABLE IF EXISTS Beds;

CREATE TABLE Patients (
    patient_id INT PRIMARY KEY,
    balance DOUBLE,
    status VARCHAR(50),
    bed_id INT
);

CREATE TABLE Beds (
    bed_id INT PRIMARY KEY,
    status VARCHAR(50)
);

INSERT INTO Beds VALUES (1, 'OCCUPIED');
INSERT INTO Patients VALUES (1, 1000, 'Đang điều trị', 1);