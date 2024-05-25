CREATE
DATABASE IF NOT EXISTS `soundsphere` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE
`soundsphere`;

CREATE TABLE `users`
(
    `id`          varchar(50)  NOT NULL ,
    `name`        varchar(255) NOT NULL,
    `sex`         enum('male', 'female') NOT NULL DEFAULT 'male',
    `description` varchar(255) NULL,
    `username`    varchar(255) NULL UNIQUE,
    `email`       varchar(255) NULL UNIQUE,
    `password`    varchar(255) NULL,
    `role`        enum('listener', 'artist', 'manager') NOT NULL DEFAULT 'listener',
    `status`      enum('normal', 'block', 'pending') NOT NULL DEFAULT 'normal',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `songs`
(
    `id`         varchar(50)   NOT NULL ,
    `title`       varchar(255) NOT NULL,
    `id_artist`   varchar(50) NOT NULL,
    `genre_id`    varchar(50)         NOT NULL,
    `description` varchar(255)  NULL,
    `time_play`   int           NULL DEFAULT 0,
    `song_data`   longblob      NULL,
    `release_day` date          NULL,
    `lyric`       varchar(255)  NULL,
    `rating`      float         NULL DEFAULT 0,
    CHECK (rating >= 0 AND rating <= 10),
    `status`      enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    FOREIGN KEY (`id_artist`) REFERENCES `users` (`id`),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `genre`
(
    `id`     varchar(50) NOT NULL ,
    `name`   varchar(255) NOT NULL,
    `status` enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `playlists`
(
    `id`      varchar(50) NOT NULL ,
    `name`    varchar(255) NOT NULL,
    `user_id` varchar(50)        NOT NULL,
    `type`    enum('playlist', 'album') NOT NULL DEFAULT 'playlist',
    `status`  enum('unavailable', 'available', 'deleted') NOT NULL DEFAULT 'available',
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `playlist_songs`
(
    `playlist_id` varchar(50) NOT NULL,
    `song_id`    varchar(50) NOT NULL,
    PRIMARY KEY (`playlist_id`, `song_id`),
    FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`id`),
    FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_listened`
(
    `id`      varchar(50) NOT NULL ,
    `user_id` varchar(50) NOT NULL,
    `song_id` varchar(50) NOT NULL,
    `count`   int NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `soundsphere`.`songs`
    ADD COLUMN `image` LONGBLOB NULL AFTER `status`;
