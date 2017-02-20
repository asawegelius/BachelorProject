CREATE DEFINER = CURRENT_USER TRIGGER `identity_db`.`users_AFTER_INSERT` AFTER INSERT ON `users` FOR EACH ROW
BEGIN
insert into `access_db`.user(user_id, user_name) values (NEW.users_id, NEW.email);
END



CREATE DEFINER = CURRENT_USER TRIGGER `identity_db`.`users_AFTER_UPDATE` AFTER UPDATE ON `users` FOR EACH ROW
BEGIN
update `access_db`.user set user_name = NEW.email;
END




CREATE DEFINER = CURRENT_USER TRIGGER `identity_db`.`users_BEFORE_DELETE` BEFORE DELETE ON `users` FOR EACH ROW
BEGIN
DELETE FROM `access_db`.user
    WHERE user.user_name = OLD.email;
END




