CREATE SCHEMA `product_db` ;

CREATE TABLE `product_db`.`products` (
  `id` INT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `category` VARCHAR(45) NULL,
  `price` DOUBLE NULL,
  `specs` LONGTEXT NULL,
  PRIMARY KEY (`id`));



