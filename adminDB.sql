CREATE
DATABASE IF NOT EXISTS `soundsphere` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE
`soundsphere`;

CREATE TABLE `users`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL,
    `sex`         enum('male', 'female') NOT NULL DEFAULT 'male',
    `birthday`    date         NOT NULL,
    `description` varchar(255) NOT NULL,
    `username`    varchar(255) NOT NULL UNIQUE,
    `email`       varchar(255) NOT NULL UNIQUE,
    `password`    varchar(255) NOT NULL,
    `role`        enum('listener', 'artist', 'manager') NOT NULL DEFAULT 'listener',
    `status`      enum('normal', 'block', 'pending') NOT NULL DEFAULT 'normal',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `songs`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `title`       varchar(255) NOT NULL,
    `id_artist`   int          NOT NULL,
    `genre_id`    int          NOT NULL,
    `description` varchar(255) NOT NULL,
    `time_play`   int          NOT NULL DEFAULT 0,
    `song_data`   longblob     NOT NULL,
    `release_day` date         NOT NULL,
    `lyric`       varchar(255) NOT NULL,
    `rating`      float        NOT NULL DEFAULT 0,
    CHECK (rating >= 0 AND rating <= 10),
    `status`      enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    FOREIGN KEY (`id_artist`) REFERENCES `users` (`id`),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `genre`
(
    `id`     int          NOT NULL AUTO_INCREMENT,
    `name`   varchar(255) NOT NULL,
    `status` enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `playlists`
(
    `id`      int          NOT NULL AUTO_INCREMENT,
    `name`    varchar(255) NOT NULL,
    `user_id` int          NOT NULL,
    `type`    enum('playlist', 'album') NOT NULL DEFAULT 'playlist',
    `status`  enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `playlist_songs`
(
    `playlist_id` int NOT NULL,
    `song_id`     int NOT NULL,
    PRIMARY KEY (`playlist_id`, `song_id`),
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`id`),
    FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_listened`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `song_id` int NOT NULL,
    `count`   int NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELIMITER
$$
CREATE TRIGGER `before_insert_users`
    BEFORE INSERT
    ON `users`
    FOR EACH ROW
BEGIN
    IF NEW.role = 'artist' THEN
        SET NEW.status = 'pending';
END IF;
IF
NEW.birthday >= CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The birthday cannot be in the future';
END IF;
END$$
DELIMITER ;
