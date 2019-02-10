-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`tipos_documentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tipos_documentos` (
  `id_tipodocumento` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(200) NOT NULL,
  `abreviatura` VARCHAR(5) NOT NULL,
  `validar_como_cuit` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_tipodocumento`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`provincias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`provincias` (
  `id_provincia` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(400) NOT NULL,
  `region` CHAR(3) NOT NULL,
  PRIMARY KEY (`id_provincia`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`localidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`localidades` (
  `id_localidad` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(300) NOT NULL,
  `id_provincia` INT NOT NULL,
  `codigo_postal` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id_localidad`),
  INDEX `fk_provincias_id_provincia_idx` (`id_provincia` ASC),
  UNIQUE INDEX `localidades_nombre_id_provincia_UNIQUE` (`nombre` ASC, `id_provincia` ASC),
  CONSTRAINT `fk_provincias_id_provincia`
    FOREIGN KEY (`id_provincia`)
    REFERENCES `mydb`.`provincias` (`id_provincia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`personas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`personas` (
  `id_tipodocumento` INT NOT NULL,
  `numero_documento` INT NOT NULL,
  `nombre_apellido` VARCHAR(400) NOT NULL,
  `fecha_nacimiento` DATE NOT NULL,
  `genero` CHAR(1) NOT NULL,
  `es_argentino` TINYINT(1) NOT NULL,
  `correo_electronico` VARCHAR(300) NULL,
  `foto_cara` BLOB NULL,
  `id_localidad` INT NOT NULL,
  `codigo_postal` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id_tipodocumento`, `numero_documento`),
  UNIQUE INDEX `nombre_apellido_UNIQUE` (`nombre_apellido` ASC),
  INDEX `fk_localidades_id_localidad_idx` (`id_localidad` ASC),
  CONSTRAINT `fk_tipos_documentos_id_documento`
    FOREIGN KEY (`id_tipodocumento`)
    REFERENCES `mydb`.`tipos_documentos` (`id_tipodocumento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_localidades_id_localidad`
    FOREIGN KEY (`id_localidad`)
    REFERENCES `mydb`.`localidades` (`id_localidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`usuarios` (
  `id_tipodocumento` INT NOT NULL,
  `numero_documento` INT NOT NULL,
  `nombre_usuario` VARCHAR(50) NOT NULL,
  `hashed_pwd` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id_tipodocumento`, `numero_documento`),
  UNIQUE INDEX `nombre_usuario_UNIQUE` (`nombre_usuario` ASC),
  CONSTRAINT `fk_usuarios_personas`
    FOREIGN KEY (`id_tipodocumento` , `numero_documento`)
    REFERENCES `mydb`.`personas` (`id_tipodocumento` , `numero_documento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- User
-- -----------------------------------------------------
create user 'mydbuser'@'%' identified by 'mydbuser';
grant all on mydb.* to 'mydbuser'@'%';
revoke all on mydb.* from 'mydbuser'@'%';
grant select, insert, delete, update on mydb.* to 'mydbuser'@'%';

