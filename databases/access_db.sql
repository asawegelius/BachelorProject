DROP DATABASE IF EXISTS `access_db`;
CREATE DATABASE `access_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_danish_ci */;
USE `access_db`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `client_id` int(11) NOT NULL,
  `client_name` varchar(45) COLLATE utf8_danish_ci DEFAULT NULL,
  `client_secret` varchar(90) COLLATE utf8_danish_ci DEFAULT NULL,
  `client_description` varchar(2048) COLLATE utf8_danish_ci DEFAULT NULL,
  `redirect_uri` varchar(1024) CHARACTER SET utf8 DEFAULT NULL,
  `grant_type` enum('AUTHORIZATION_CODE','CLIENT_CREDENTIALS','PASSWORD','REFRESH_TOKEN') CHARACTER SET utf8 DEFAULT NULL,
  `client_user_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  KEY `owner_id_idx` (`client_user_fk`),
  CONSTRAINT `client_user_fk` FOREIGN KEY (`client_user_fk`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

DROP TABLE IF EXISTS  `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `role_description` varchar(512) COLLATE utf8_danish_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `object` varchar(60) COLLATE utf8_danish_ci DEFAULT NULL,
  `target` enum('CREATE','READ','UPDATE','DELETE') CHARACTER SET utf8 DEFAULT NULL,
  `permission_client_fk` int(11) NOT NULL,
  PRIMARY KEY (`permission_id`),
  KEY `client_id_idx` (`permission_client_fk`),
  CONSTRAINT `permission_client_fk` FOREIGN KEY (`permission_client_fk`) REFERENCES `client` (`client_id`) ON DELETE cascade ON UPDATE cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_role_user_fk` int(11) DEFAULT NULL,
  `user_role_role_fk` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `user_id_idx` (`user_role_user_fk`),
  KEY `role_id_idx` (`user_role_role_fk`),
  CONSTRAINT `user_role_role_fk` FOREIGN KEY (`user_role_role_fk`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_role_user_fk` FOREIGN KEY (`user_role_user_fk`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_permission_id` int(11) NOT NULL,
  `role_permission_permission_fk` int(11) NOT NULL,
  `role_permission_role_fk` int(11) NOT NULL,
  PRIMARY KEY (`role_permission_id`),
  KEY `role_permission_permission_fk_idx` (`role_permission_permission_fk`),
  KEY `role_permission_role_fk_idx` (`role_permission_role_fk`),
  CONSTRAINT `role_permission_permission_fk` FOREIGN KEY (`role_permission_permission_fk`) REFERENCES `permission` (`permission_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role_permission_role_fk` FOREIGN KEY (`role_permission_role_fk`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;







