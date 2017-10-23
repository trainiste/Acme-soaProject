SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `AcmeDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `AcmeDB` ;

-- -----------------------------------------------------
-- Table `AcmeDB`.`accessori`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AcmeDB`.`accessori` ;

CREATE TABLE IF NOT EXISTS `AcmeDB`.`accessori` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AcmeDB`.`componenti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AcmeDB`.`componenti` ;

CREATE TABLE IF NOT EXISTS `AcmeDB`.`componenti` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Tipo` VARCHAR(45) NOT NULL,
  `Valore` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AcmeDB`.`magazzini`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AcmeDB`.`magazzini` ;

CREATE TABLE IF NOT EXISTS `AcmeDB`.`magazzini` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Tipo` CHAR NOT NULL,
  `Latitudine` DOUBLE NOT NULL,
  `Longitudine` DOUBLE NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AcmeDB`.`am`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AcmeDB`.`am` ;

CREATE TABLE IF NOT EXISTS `AcmeDB`.`am` (
  `Id` INT NOT NULL,
  `Magazzini_Id` INT NOT NULL,
  `Accessori_Id` INT NOT NULL,
  `Presente` TINYINT(1) NULL,
  PRIMARY KEY (`Id`, `Magazzini_Id`, `Accessori_Id`),
  INDEX `fk_AM_Accessori_idx` (`Accessori_Id` ASC),
  INDEX `fk_AM_Magazzini1_idx` (`Magazzini_Id` ASC),
  CONSTRAINT `fk_AM_Accessori`
    FOREIGN KEY (`Accessori_Id`)
    REFERENCES `AcmeDB`.`accessori` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AM_Magazzini1`
    FOREIGN KEY (`Magazzini_Id`)
    REFERENCES `AcmeDB`.`magazzini` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AcmeDB`.`cm`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AcmeDB`.`cm` ;

CREATE TABLE IF NOT EXISTS `AcmeDB`.`cm` (
  `Id` INT NOT NULL,
  `Magazzini_Id` INT NOT NULL,
  `Componenti_Id` INT NOT NULL,
  `Presente` TINYINT(1) NOT NULL,
  PRIMARY KEY (`Id`, `Magazzini_Id`, `Componenti_Id`),
  INDEX `fk_CM_Magazzini1_idx` (`Magazzini_Id` ASC),
  INDEX `fk_CM_Componenti1_idx` (`Componenti_Id` ASC),
  CONSTRAINT `fk_CM_Magazzini1`
    FOREIGN KEY (`Magazzini_Id`)
    REFERENCES `AcmeDB`.`magazzini` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CM_Componenti1`
    FOREIGN KEY (`Componenti_Id`)
    REFERENCES `AcmeDB`.`componenti` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `AcmeDB`.`accessori`
-- -----------------------------------------------------
START TRANSACTION;
USE `AcmeDB`;
INSERT INTO `AcmeDB`.`accessori` (`Id`, `Nome`) VALUES (1, 'campanello');
INSERT INTO `AcmeDB`.`accessori` (`Id`, `Nome`) VALUES (2, 'cestino');
INSERT INTO `AcmeDB`.`accessori` (`Id`, `Nome`) VALUES (3, 'cavalletto');

COMMIT;


-- -----------------------------------------------------
-- Data for table `AcmeDB`.`componenti`
-- -----------------------------------------------------
START TRANSACTION;
USE `AcmeDB`;
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (1, 'ModelloBase', 'BMX');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (2, 'ModelloBase', 'CityBike');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (3, 'ModelloBase', 'MountainBike');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (4, 'ModelloBase', 'Corsa');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (5, 'Colorazione', 'Blu');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (6, 'Colorazione', 'Rosso');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (7, 'Colorazione', 'Giallo');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (8, 'Colorazione', 'Verde');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (9, 'Colorazione', 'Rosa');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (10, 'Freno', 'Tamburo');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (11, 'Freno', 'Disco');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (12, 'Sterzo', 'Standard');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (13, 'Sterzo', 'Integrato');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (14, 'Trasmissione', 'ScattoFisso');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (15, 'Trasmissione', 'SingleSpeed');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (16, 'Trasmissione', 'DoppiaCorsa');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (17, 'Trasmissione', 'DoppiaMountain');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (18, 'Ammortizzatore', 'Aria');
INSERT INTO `AcmeDB`.`componenti` (`Id`, `Tipo`, `Valore`) VALUES (19, 'Ammortizzatore', 'Molla');

COMMIT;


-- -----------------------------------------------------
-- Data for table `AcmeDB`.`magazzini`
-- -----------------------------------------------------
START TRANSACTION;
USE `AcmeDB`;
INSERT INTO `AcmeDB`.`magazzini` (`Id`, `Nome`, `Tipo`, `Latitudine`, `Longitudine`) VALUES (1, 'Bologna', 'P', 44.4938100, 11.3387500);
INSERT INTO `AcmeDB`.`magazzini` (`Id`, `Nome`, `Tipo`, `Latitudine`, `Longitudine`) VALUES (2, 'Milano', 'S', 45.4642700, 9.1895100);
INSERT INTO `AcmeDB`.`magazzini` (`Id`, `Nome`, `Tipo`, `Latitudine`, `Longitudine`) VALUES (3, 'Napoli', 'S', 40.8563100, 14.2464100);

COMMIT;


-- -----------------------------------------------------
-- Data for table `AcmeDB`.`am`
-- -----------------------------------------------------
START TRANSACTION;
USE `AcmeDB`;
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (1, 1, 1, 0);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (2, 1, 2, 1);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (3, 1, 3, 1);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (4, 2, 1, 0);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (5, 2, 2, 1);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (6, 2, 3, 0);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (7, 3, 1, 0);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (8, 3, 2, 1);
INSERT INTO `AcmeDB`.`am` (`Id`, `Magazzini_Id`, `Accessori_Id`, `Presente`) VALUES (9, 3, 3, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `AcmeDB`.`cm`
-- -----------------------------------------------------
START TRANSACTION;
USE `AcmeDB`;
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (1, 1, 1, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (2, 1, 2, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (3, 1, 3, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (4, 1, 4, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (5, 1, 5, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (6, 1, 6, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (7, 1, 7, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (8, 1, 8, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (9, 1, 9, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (10, 1, 10, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (11, 1, 11, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (12, 1, 12, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (13, 1, 13, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (14, 1, 14, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (15, 1, 15, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (16, 1, 16, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (17, 1, 17, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (18, 1, 18, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (19, 1, 19, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (20, 2, 1, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (21, 2, 2, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (22, 2, 3, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (23, 2, 4, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (24, 2, 5, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (25, 2, 6, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (26, 2, 7, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (27, 2, 8, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (28, 2, 9, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (29, 2, 10, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (30, 2, 11, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (31, 2, 12, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (32, 2, 13, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (33, 2, 14, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (34, 2, 15, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (35, 2, 16, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (36, 2, 17, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (37, 2, 18, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (38, 2, 19, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (39, 3, 1, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (40, 3, 2, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (41, 3, 3, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (42, 3, 4, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (43, 3, 5, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (44, 3, 6, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (45, 3, 7, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (46, 3, 8, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (47, 3, 9, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (48, 3, 10, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (49, 3, 11, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (50, 3, 12, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (51, 3, 13, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (52, 3, 14, 0);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (53, 3, 15, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (54, 3, 16, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (55, 3, 17, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (56, 3, 18, 1);
INSERT INTO `AcmeDB`.`cm` (`Id`, `Magazzini_Id`, `Componenti_Id`, `Presente`) VALUES (57, 3, 19, 1);

COMMIT;

