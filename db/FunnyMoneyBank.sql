-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema FunnyMoneyBank
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `FunnyMoneyBank` ;

-- -----------------------------------------------------
-- Schema FunnyMoneyBank
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FunnyMoneyBank` DEFAULT CHARACTER SET utf8 ;
USE `FunnyMoneyBank` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `surname` VARCHAR(20) NOT NULL,
  `email` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `user` (`email` ASC) VISIBLE;

CREATE UNIQUE INDEX `code_UNIQUE` ON `user` (`code` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `wallet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wallet` ;

CREATE TABLE IF NOT EXISTS `wallet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `balance` DECIMAL(10,2) NOT NULL,
  `id_user` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_wallet_user`
    FOREIGN KEY (`id_user`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `employee` ;

CREATE TABLE IF NOT EXISTS `employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `salary` DECIMAL(10,2) NOT NULL DEFAULT 100,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_employee_user1`
    FOREIGN KEY (`id_user`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `transaction_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transaction_type` ;

CREATE TABLE IF NOT EXISTS `transaction_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `atm` ;

CREATE TABLE IF NOT EXISTS `atm` (
  `id` INT NOT NULL,
  `money` DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transaction` ;

CREATE TABLE IF NOT EXISTS `transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` DECIMAL(10,2) NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  `id_transaction_type` INT NOT NULL,
  `id_atm` INT NOT NULL,
  `id_wallet` INT NOT NULL,
  `id_wallet_target` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_transaction_wallet1`
    FOREIGN KEY (`id_wallet`)
    REFERENCES `wallet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_transaction_type1`
    FOREIGN KEY (`id_transaction_type`)
    REFERENCES `transaction_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_wallet2`
    FOREIGN KEY (`id_wallet_target`)
    REFERENCES `wallet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_atm1`
    FOREIGN KEY (`id_atm`)
    REFERENCES `atm` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
