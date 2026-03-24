CREATE DATABASE IF NOT EXISTS Hospital_Bai4;
USE Hospital_Bai4;

DROP TABLE IF EXISTS DichVuSuDung;
DROP TABLE IF EXISTS BenhNhan;

CREATE TABLE BenhNhan (
    maBenhNhan INT PRIMARY KEY,
    tenBenhNhan VARCHAR(100)
);

CREATE TABLE DichVuSuDung (
    id INT AUTO_INCREMENT PRIMARY KEY,
    maBenhNhan INT,
    tenDichVu VARCHAR(100)
);

INSERT INTO BenhNhan VALUES (1, 'Do Hoang Anh');
INSERT INTO BenhNhan VALUES (2, 'Nguyen Dang Duong');

INSERT INTO DichVuSuDung(maBenhNhan, tenDichVu) VALUES (1, 'Thuoc A');
INSERT INTO DichVuSuDung(maBenhNhan, tenDichVu) VALUES (1, 'Xet nghiem');