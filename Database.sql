CREATE DATABASE IF NOT EXISTS `soundsphere` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `soundsphere`;

CREATE TABLE IF NOT EXISTS `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` nvarchar(255) NOT NULL,
    `sex` enum('male', 'female') NOT NULL DEFAULT 'male',
    `birthday` date NOT NULL,
    `description` nvarchar(255) NOT NULL,
    'username' varchar(255) NOT NULL UNIQUE,
    `email` varchar(255) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    `role` enum('listener', 'artist','manager') NOT NULL DEFAULT 'listener',
    `status` enum('normal', 'block', 'pending') NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `songs` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `title` nvarchar(255) NOT NULL,
    `id_artist` int(11) NOT NULL,
    `genre_id` int(11) NOT NULL,
    `description` nvarchar(255) NOT NULL,
    `time_play` int(11) NOT NULL DEFAULT 0,
    `song_data` longblob NOT NULL,
    `release_day` date NOT NULL CHECK (release_day < CURRENT_TIMESTAMP()),
    `lyric` nvarchar(255) NOT NULL,
    `rating` float NOT NULL DEFAULT 0,
    CHECK (rating >= 0 AND rating <= 10),
    `status` enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    FOREIGN KEY (`id_artist`) REFERENCES `users`(`id`)
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `genre` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` nvarchar(255) NOT NULL,
    `status` enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `playlists` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` nvarchar(255) NOT NULL,
    `user_id` int(11) NOT NULL,
    `type` enum('playlist', 'album') NOT NULL DEFAULT 'playlist',
    `status` enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

CREATE TABLE IF NOT EXISTS `playlist_songs` (
    `playlist_id` int(11) NOT NULL,
    `song_id` int(11) NOT NULL,
    PRIMARY KEY (`playlist_id`, `song_id`),
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists`(`id`),
    FOREIGN KEY (`song_id`) REFERENCES `songs`(`id`)
);


CREATE TABLE IF NOT EXISTS `user_listened` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `song_id` int(11) NOT NULL,
    `count` int(11) NOT NULL DEFAULT 0 (CHECK count >= 0),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`song_id`) REFERENCES `songs`(`id`)
);

DELIMITER //
CREATE TRIGGER before_insert_users
BEFORE INSERT ON `users`
FOR EACH ROW
BEGIN
    IF NEW.role = 'artist' THEN
        SET NEW.status = 'pending';
    ELSE
        SET NEW.status = 'normal';
    END IF;
    IF NEW.birthday >= CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The birthday cannot be in the future';
    END IF;
    INSERT INTO playlists (name, user_id, status) VALUES ('Favorite', NEW.id, 'available');
END;
//
DELIMITER ;
