DROP DATABASE IF EXISTS `identity_db`;
CREATE DATABASE `identity_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_danish_ci */;
USE `identity_db`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `users_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(60) DEFAULT NULL,
  `last_name` varchar(60) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `mobile` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`users_id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

DROP TABLE IF EXISTS `logins`;
CREATE TABLE `logins` (
  `logins_id` int(11) NOT NULL AUTO_INCREMENT,
  `logins_user_fk` int(11) DEFAULT NULL,
  `password_salt` varchar(256) COLLATE utf8_danish_ci DEFAULT NULL,
  `password_hash` varchar(256) COLLATE utf8_danish_ci DEFAULT NULL,
  PRIMARY KEY (`logins_id`),
  KEY `logins_user_fk_idx` (`logins_user_fk`),
  CONSTRAINT `logins_user_fk` FOREIGN KEY (`logins_user_fk`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;
