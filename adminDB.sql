-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema musicweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema musicweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `musicweb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `musicweb` ;

-- -----------------------------------------------------
-- Table `musicweb`.`genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicweb`.`genre` (
                                                  `id` INT NOT NULL,
                                                  `name` VARCHAR(255) NOT NULL,
    `status` ENUM('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `musicweb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicweb`.`users` (
    `name` VARCHAR(255) CHARACTER SET 'utf8mb3' NOT NULL,
    `sex` ENUM('male', 'female') NULL DEFAULT 'male',
    `description` VARCHAR(255) NULL DEFAULT NULL,
    `username` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('listener', 'artist', 'manager') NULL DEFAULT 'listener',
    `status` ENUM('normal', 'block', 'pending') NULL DEFAULT 'normal',
    PRIMARY KEY (`username`),
    UNIQUE INDEX `username` (`username` ASC) VISIBLE,
    UNIQUE INDEX `email` (`email` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicweb`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicweb`.`playlists` (
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `name` VARCHAR(255) NULL DEFAULT NULL,
    `user_id` VARCHAR(255) NULL DEFAULT NULL,
    `type` ENUM('playlist', 'album') NULL DEFAULT 'playlist',
    `status` ENUM('unavailable', 'available', 'deleted') NULL DEFAULT 'available',
    PRIMARY KEY (`id`),
    INDEX `user_id` (`user_id` ASC) VISIBLE,
    CONSTRAINT `playlists_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `musicweb`.`users` (`username`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 21
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicweb`.`rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicweb`.`rating` (
    `user_id` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `song_id` INT NULL DEFAULT NULL,
    `rating` FLOAT NULL DEFAULT NULL)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicweb`.`song_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicweb`.`song_detail` (
                                                        `song_id` INT NULL DEFAULT NULL,
                                                        `written_by` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `produced_by` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `date_release` DATE NULL DEFAULT NULL)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicweb`.`songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicweb`.`songs` (
                                                  `id` INT NULL DEFAULT NULL,
                                                  `title` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `id_artist` VARCHAR(255) NULL DEFAULT NULL,
    `genre_id` INT NULL DEFAULT NULL,
    `description` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL,
    `time_play` INT NULL DEFAULT '0',
    `song_data` TEXT NULL DEFAULT NULL,
    `image` TEXT NULL DEFAULT NULL,
    `lyric` VARCHAR(255) NULL DEFAULT NULL,
    `rating` FLOAT NULL DEFAULT '0',
    `status` VARCHAR(255) CHARACTER SET 'utf8mb3' NULL DEFAULT NULL)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `musicweb`.`user_listened`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicweb`.`user_listened` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `user_id` VARCHAR(255) NOT NULL,
    `song_id` INT NOT NULL,
    `count` INT NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



