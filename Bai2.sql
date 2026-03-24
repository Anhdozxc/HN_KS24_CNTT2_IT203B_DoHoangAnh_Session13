CREATE DATABASE IF NOT EXISTS Hospital_Bai2;
USE Hospital_Bai2;

DROP TABLE IF EXISTS Patient_Wallet;
DROP TABLE IF EXISTS Invoices;

CREATE TABLE Patient_Wallet (
    patient_id INT PRIMARY KEY,
    balance DOUBLE
);

CREATE TABLE Invoices (
    invoice_id INT PRIMARY KEY,
    status VARCHAR(50)
);

INSERT INTO Patient_Wallet VALUES (1, 1000);
INSERT INTO Invoices VALUES (1, 'UNPAID');