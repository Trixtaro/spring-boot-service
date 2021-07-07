CREATE DATABASE `justclickmediatest`;

USE `justclickmediatest`;

CREATE TABLE `justclickmediatest`.`link` (
  `id_link` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `remaining_calls` INT NOT NULL,
  PRIMARY KEY (`id_link`));

INSERT INTO `justclickmediatest`.`link` (`value`, `url`, `remaining_calls`) VALUES ('google', 'https://google.com', '5');
INSERT INTO `justclickmediatest`.`link` (`value`, `url`, `remaining_calls`) VALUES ('facebook', 'https://facebook.com', '10');
INSERT INTO `justclickmediatest`.`link` (`value`, `url`, `remaining_calls`) VALUES ('justclick', 'https://www.justclick.media/', '20');

