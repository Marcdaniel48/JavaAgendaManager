-- Marc-Daniel Dialogo
-- 1539756

DROP DATABASE IF EXISTS JAMDB;
CREATE DATABASE JAMDB;

CREATE USER 'MarcDaniel'@'localhost' IDENTIFIED BY 'dbsurfing';
GRANT ALL ON JAMDB.* TO 'MarcDaniel'@'localhost';
FLUSH PRIVILEGES;

USE JAMDB;
