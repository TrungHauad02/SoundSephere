CREATE DATABASE IF NOT EXISTS `soundsphere` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `soundsphere`;

CREATE TABLE [users] (
    [id] int IDENTITY(1,1) NOT NULL,
    [name] nvarchar(255) NOT NULL,
    [sex] nvarchar(6) CHECK (sex IN ('male', 'female')) DEFAULT ('male'),
    [birthday] date NOT NULL,
    [description] nvarchar(255) NOT NULL,
    [username] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL UNIQUE,
    [email] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL UNIQUE,
    [password] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
    [role] nvarchar(10) CHECK (role IN ('listener', 'artist','manager')) DEFAULT ('listener'),
    [status] nvarchar(10) CHECK (status IN ('normal', 'block', 'pending')) DEFAULT ('normal'),
    PRIMARY KEY ([id])
);

CREATE TABLE [songs] (
    [id] int IDENTITY(1,1) NOT NULL,
    [title] nvarchar(255) NOT NULL,
    [id_artist] int NOT NULL,
    [genre_id] int NOT NULL,
    [description] nvarchar(255) NOT NULL,
    [time_play] int NOT NULL DEFAULT 0,
    [song_data] varbinary(max) NOT NULL,
    [release_day] date NOT NULL CHECK (release_day < GETDATE()),
    [lyric] nvarchar(255) NOT NULL,
    [rating] float NOT NULL DEFAULT 0,
    CHECK (rating >= 0 AND rating <= 10),
    [status] nvarchar(10) CHECK (status IN ('unavailable', 'available', 'deleted')) DEFAULT ('available'),
    FOREIGN KEY ([id_artist]) REFERENCES [users]([id]),
    PRIMARY KEY ([id])
);

CREATE TABLE [genre] (
    [id] int IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [name] nvarchar(255) NOT NULL,
    [status] nvarchar(10) CHECK (status IN ('unavailable', 'available', 'deleted')) DEFAULT ('available')
);

CREATE TABLE [playlists] (
    [id] int IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [name] nvarchar(255) NOT NULL,
    [user_id] int NOT NULL,
    [type] nvarchar(10) CHECK (type IN ('playlist', 'album')) DEFAULT ('playlist'),
    [status] nvarchar(10) CHECK (status IN ('unavailable', 'available', 'deleted')) DEFAULT ('available'),
    FOREIGN KEY ([user_id]) REFERENCES [users]([id])
);

CREATE TABLE [playlist_songs] (
    [playlist_id] int NOT NULL,
    [song_id] int NOT NULL,
    CONSTRAINT [PK_playlist_songs] PRIMARY KEY ([playlist_id], [song_id]),
    FOREIGN KEY ([playlist_id]) REFERENCES [playlists]([id]),
    FOREIGN KEY ([song_id]) REFERENCES [songs]([id])
);


CREATE TABLE [user_listened] (
    [id] int NOT NULL IDENTITY(1,1),
    [user_id] int NOT NULL,
    [song_id] int NOT NULL,
    [count] int NOT NULL CONSTRAINT [DF_user_listened_count] DEFAULT 0,
    CONSTRAINT [PK_user_listened] PRIMARY KEY ([id]),
    CONSTRAINT [FK_user_listened_users_user_id] FOREIGN KEY ([user_id]) REFERENCES [users]([id]),
    CONSTRAINT [FK_user_listened_songs_song_id] FOREIGN KEY ([song_id]) REFERENCES [songs]([id])
);

CREATE TRIGGER [before_insert_users]
ON [users]
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @new_role NVARCHAR(10) = (SELECT i.role FROM inserted i);
    DECLARE @new_birthday DATE = (SELECT i.birthday FROM inserted i);
    INSERT INTO [users] ([name], [sex], [birthday], [description], [username], [email], [password], [role], [status])
    SELECT [name], [sex], [birthday], [description], [username], [email], [password],
        CASE WHEN @new_role = 'artist' THEN 'pending' ELSE 'normal' END,
        CASE WHEN @new_birthday >= GETDATE() THEN 'The birthday cannot be in the future' ELSE 'normal' END
    FROM inserted;
    INSERT INTO playlists (name, user_id, status)
    SELECT 'Favorite', inserted.id, 'available'
    FROM inserted;
END;

