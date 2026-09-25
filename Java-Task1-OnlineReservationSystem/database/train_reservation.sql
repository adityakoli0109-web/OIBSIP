CREATE DATABASE IF NOT EXISTS train_reservation;
USE train_reservation;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE trains (
    train_number INT PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL
);

CREATE TABLE reservations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pnr VARCHAR(20) NOT NULL UNIQUE,
    passenger_name VARCHAR(100) NOT NULL,
    train_number INT NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    class_type VARCHAR(30) NOT NULL,
    journey_date DATE NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL
);

INSERT INTO users (username, password)
VALUES ('admin', 'admin123');

INSERT INTO trains (train_number, train_name) VALUES
(1001, 'Mumbai Express'),
(1002, 'Deccan Express'),
(1003, 'Pune Intercity'),
(1004, 'Konkan Express'),
(1005, 'Sahyadri Express');
