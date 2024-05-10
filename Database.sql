IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'soundsphere')
BEGIN
    CREATE DATABASE soundsphere
    COLLATE SQL_Latin1_General_CP1_CI_AS;
END;
GO
USE soundsphere;
USE `soundsphere`;

CREATE TABLE [users] (
    [id] int IDENTITY(1,1) NOT NULL,
    [name] nvarchar(255) NOT NULL,
    [sex] nvarchar(6) CHECK (sex IN ('male', 'female')) DEFAULT ('male'),
    [birthday] date NULL, --bỏ đi
    [description] nvarchar(255) NULL, --bỏ notNull
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
    [song_data] TEXT NOT NULL,
    [image] TEXT NOT NULL, --Save url to file in firebase
    [lyric] varchar(MAX) NOT NULL, --Save url to file in firebase
    [rating] float NOT NULL DEFAULT 0,
    CHECK (rating >= 0 AND rating <= 10),
    [status] nvarchar(10) CHECK (status IN ('unavailable', 'available', 'deleted')) DEFAULT ('available'),
    FOREIGN KEY ([id_artist]) REFERENCES [users]([id]),
    PRIMARY KEY ([id])
);

CREATE TABLE [song_detail] (
    [song_id] int NOT NULL,
    [written_by] nvarchar(255) NOT NULL,
    [produced_by] nvarchar(255) NOT NULL,
    [date_release] date NOT NULL CHECK (date_release < GETDATE()),
    CONSTRAINT [PK_song_detail] PRIMARY KEY ([song_id]),
    CONSTRAINT [FK_song_detail_songs_song_id] FOREIGN KEY ([song_id]) REFERENCES [songs]([id])
);

CREATE TABLE [rating] (
    [user_id] int NOT NULL,
    [song_id] int NOT NULL,
    [rating] float NOT NULL CHECK (rating >= 0 AND rating <= 10),
    CONSTRAINT [PK_rating] PRIMARY KEY ([user_id], [song_id]),
    CONSTRAINT [FK_rating_users_user_id] FOREIGN KEY ([user_id]) REFERENCES [users]([id]),
    CONSTRAINT [FK_rating_songs_song_id] FOREIGN KEY ([song_id]) REFERENCES [songs]([id])
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

CREATE TRIGGER [after_insert_rating]
ON [rating]
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE songs
    SET rating = (
        SELECT AVG(rating) 
        FROM rating 
        WHERE song_id = inserted.song_id)
    WHERE id = inserted.id;
END;
