CREATE TABLE `apps` (
  `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `appname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `orgs` (
  `org_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `orgname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `userpassword` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `userpassword_idx` (`userpassword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `speedvoter_ludomaniregisteraktivludomaniregisteraktivuserrights` (
  `userrights_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  `org` int(11) DEFAULT NULL,
  `active` bit(1) DEFAULT 0,
  PRIMARY KEY (`userrights_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


