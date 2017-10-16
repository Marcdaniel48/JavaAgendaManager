-- Author:  Marc-Daniel
-- Created: 15-Sep-2017
USE JAMDB;

DROP TABLE IF EXISTS SMTP_SETTINGS;
DROP TABLE IF EXISTS GROUP_RECORD;
DROP TABLE IF EXISTS APPOINTMENT;

CREATE TABLE SMTP_SETTINGS
(
	SMTP_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	USERNAME VARCHAR(30) NOT NULL,
	EMAIL VARCHAR(60) NOT NULL DEFAULT '',
	EMAIL_PASSWORD VARCHAR(100) NOT NULL DEFAULT '',
	SMTP_URL VARCHAR(60) NOT NULL DEFAULT '',
	SMTP_PORT INT NOT NULL DEFAULT 465,
        DEFAULT_SMTP BOOLEAN NOT NULL DEFAULT 0,
	REMINDER_INTERVAL INT DEFAULT 0
);

CREATE TABLE GROUP_RECORD
(
	GROUP_NUMBER INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	GROUP_NAME VARCHAR(30) NOT NULL DEFAULT '',
	COLOUR VARCHAR(16) NOT NULL DEFAULT ''
);

CREATE TABLE APPOINTMENT
(
	APPOINTMENT_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	TITLE VARCHAR(30) NOT NULL,
	LOCATION VARCHAR(30) DEFAULT '',
	START_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	END_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	DETAILS VARCHAR(200) DEFAULT '',
	WHOLE_DAY BOOLEAN NOT NULL DEFAULT 0,
	APPOINTMENT_GROUP INT DEFAULT 5,
	ALARM_REMINDER BOOLEAN DEFAULT 0
);
