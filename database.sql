-- UserName For Admin is -> Admin
-- Passwor FOR Admin is -> Admin1234

CREATE DATABASE fitness_tracker;
USE fitness_tracker;

CREATE TABLE Users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

CREATE TABLE Workouts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    workout_type VARCHAR(100),
    duration INT,
    calories_burned int,
    Date date,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE Challenges (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    description TEXT
);

CREATE TABLE UserChallenges (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    challenge_id INT,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (challenge_id) REFERENCES Challenges(id)
);

CREATE TABLE IF NOT EXISTS SystemSettings (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	setting_name VARCHAR(255), 
	setting_value VARCHAR(255)
);

CREATE TABLE Feedback (
	id INT AUTO_INCREMENT Primary Key,
	User_id INT,
	Feedback TEXT,
	FOREIGN KEY (User_id) REFERENCES Users(id)
);