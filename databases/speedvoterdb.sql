CREATE TABLE `orgs` (
  `org_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `orgname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `events` (
  `event_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `event_name` varchar(45) DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `events_org_id_fk` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `events_org_id_fk_idx` (`events_org_id_fk`),
  CONSTRAINT `events_org_id_fk` FOREIGN KEY (`events_org_id_fk`) REFERENCES `orgs` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `elections` (
  `election_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `post` varchar(45) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `min_votes` int(11) DEFAULT NULL,
  `max_votes` int(11) DEFAULT NULL,
  `secret` bit(1) DEFAULT 1,
  `active` bit(1) DEFAULT 0,
  `elections_event_id_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`election_id`),
  KEY `elections_event_id_fk_idx` (`elections_event_id_fk`),
  CONSTRAINT `elections_event_id_fk` FOREIGN KEY (`elections_event_id_fk`) REFERENCES `events` (`event_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `voter` (
  `voter_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `member_code` varchar(10) DEFAULT NULL,
  `name` varchar(120) DEFAULT NULL,
  `mail` varchar(60) DEFAULT NULL,
  `mobil` varchar(12) DEFAULT NULL,
  `voter_org_id_fk` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`voter_id`),
  KEY `voter_org_id_fk_idx` (`voter_org_id_fk`),
  CONSTRAINT `voter_org_id_fk` FOREIGN KEY (`voter_org_id_fk`) REFERENCES `orgs` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `gate` (
  `gate_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gate_event_id_fk` int(10) unsigned NOT NULL,
  `gate_voter_id_fk` int(10) unsigned NOT NULL,
  `access_code` varchar(16) DEFAULT NULL,
  `active` bit(1) DEFAULT 0,
  PRIMARY KEY (`gate_id`),
  UNIQUE KEY `access_code_UNIQUE` (`access_code`),
  KEY `gate_event_id_fk_idx` (`gate_event_id_fk`),
  KEY `gate_voter_id_fk_idx` (`gate_voter_id_fk`),
  CONSTRAINT `gate_event_id_fk` FOREIGN KEY (`gate_event_id_fk`) REFERENCES `events` (`event_id`),
  CONSTRAINT `gate_voter_id_fk` FOREIGN KEY (`gate_voter_id_fk`) REFERENCES `voter` (`voter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `options` (
  `option_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `the_option` varchar(45) DEFAULT NULL,
  `options_election_id_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`option_id`),
  KEY `options_election_id_fk_idx` (`options_election_id_fk`),
  CONSTRAINT `options_election_id_fk` FOREIGN KEY (`options_election_id_fk`) REFERENCES `elections` (`election_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `series` (
  `series_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `access_code` varchar(16) DEFAULT NULL,
  `serie` varchar(2) DEFAULT NULL,
  `vacant` bit(1) DEFAULT 1,
  PRIMARY KEY (`series_id`),
  UNIQUE KEY `access_code_UNIQUE` (`access_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `speakers` (
  `speakers_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `speakers_access_code_fk` varchar(16) DEFAULT NULL,
  `speakers_event_id_fk` int(10) unsigned NOT NULL,
  `time` time DEFAULT NULL,
  PRIMARY KEY (`speakers_id`),
  KEY `speakers_access_code_fk_idx` (`speakers_access_code_fk`),
  KEY `speakers_event_id_fk_idx` (`speakers_event_id_fk`),
  CONSTRAINT `speakers_access_code_fk` FOREIGN KEY (`speakers_access_code_fk`) REFERENCES `gate` (`access_code`) ,
  CONSTRAINT `speakers_event_id_fk` FOREIGN KEY (`speakers_event_id_fk`) REFERENCES `events` (`event_id`)
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

CREATE TABLE `userrights` (
  `userrights_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  `org` int(11) DEFAULT NULL,
  `active` bit(1) DEFAULT 0,
  PRIMARY KEY (`userrights_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users_userrights` (
  `users_userrights_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `users_userrights_userrights_id_fk` int(10) unsigned NOT NULL,
  `users_userrights_user_id_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`users_userrights_id`),
  KEY `users_userrights_userrights_id_fk_idx` (`users_userrights_userrights_id_fk`),
  KEY `users_userrights_user_id_fk_idx` (`users_userrights_user_id_fk`),
  CONSTRAINT `users_userrights_userrights_id_fk` FOREIGN KEY (`users_userrights_userrights_id_fk`) REFERENCES `userrights` (`userrights_id`),
  CONSTRAINT `users_userrights_user_id_fk` FOREIGN KEY (`users_userrights_user_id_fk`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `votes` (
  `vote_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `votes_option_id_fk` int(10) unsigned NOT NULL,
  `votes_election_id_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`vote_id`),
  KEY `votes_option_id_idx` (`votes_option_id_fk`),
  KEY `votes_election_id_fk_idx` (`votes_election_id_fk`),
  CONSTRAINT `votes_election_id_fk` FOREIGN KEY (`votes_election_id_fk`) REFERENCES `elections` (`election_id`) ,
  CONSTRAINT `votes_option_id_fk` FOREIGN KEY (`votes_option_id_fk`) REFERENCES `options` (`option_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `voters_votes` (
  `voters_vote_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `voters_votes_access_code_fk` varchar(16) NOT NULL,
  `voters_votes_vote_id_fk` int(10) unsigned NOT NULL,
  PRIMARY KEY (`voters_vote_id`),
  KEY `voters_votes_access_code_fk_idx` (`voters_votes_access_code_fk`),
  KEY `voters_votes_vote_id_fk_idx` (`voters_votes_vote_id_fk`),
  CONSTRAINT `voters_votes_access_code_fk` FOREIGN KEY (`voters_votes_access_code_fk`) REFERENCES `gate` (`access_code`) ,
  CONSTRAINT `voters_votes_vote_id_fk` FOREIGN KEY (`voters_votes_vote_id_fk`) REFERENCES `votes` (`vote_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


