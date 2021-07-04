CREATE TABLE `justclickmedia`.`link` (
  `id_link` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `remaining_calls` INT NOT NULL,
  PRIMARY KEY (`id_link`));

