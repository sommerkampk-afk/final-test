-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************

DROP TABLE IF EXISTS users;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	address varchar(100) NULL,
	city varchar(50) NULL,
	state_code char(2) NULL,
	zip varchar(5) NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE games (
    objectid SERIAL,
    name varchar(255) NOT NULL,
    play_time integer,
    min_num_players integer,
    max_num_players integer,
    age integer,
    publisher varchar(255),
    user_id integer NOT NULL,
    avg_weight integer,
    retired boolean DEFAULT FALSE,
    CONSTRAINT PK_games PRIMARY KEY (objectid),
    CONSTRAINT FK_games_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE plays (
    playid SERIAL,
    userid integer NOT NULL,
    objectid integer NOT NULL,
    winner text,
    legacy_developments text,
    game_notes text,
    game_rating integer,
    CONSTRAINT PK_plays PRIMARY KEY (playid),
    CONSTRAINT FK_plays_user FOREIGN KEY (userid) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT FK_plays_game FOREIGN KEY (objectid) REFERENCES games(objectid) ON DELETE CASCADE
);

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES 
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER',  'Jack O''Lantern', null, 'Cleveland', 'OH', '44123'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN', 'Jill O''Lantern', null, 'Beverly Hills', 'CA', '90210');

INSERT INTO games (name, play_time, min_num_players, max_num_players, age, publisher, user_id, avg_weight, retired)
VALUES
    ('Catan', 75, 3, 4, 10, 'Catan Studio', 1, 3, FALSE),
    ('Azul', 45, 2, 4, 8, 'Plan B Games', 1, 2, FALSE),
    ('Wingspan', 70, 1, 5, 10, 'Stonemaier Games', 1, 3, FALSE),
    ('7 Wonders', 30, 2, 7, 10, 'Repos Production', 2, 3, FALSE),
    ('Pandemic', 45, 2, 4, 8, 'Z-Man Games', 2, 3, FALSE);

INSERT INTO plays (userid, objectid, winner, legacy_developments, game_notes, game_rating)
VALUES
    (1, 1, 'Alice', 'Built longest road', 'Great game with friends', 9),
    (1, 2, 'Bob', NULL, 'Beautiful tile placement', 8),
    (2, 4, 'Charlie', 'Wonder combo worked well', 'Quick and fun', 7),
    (2, 5, 'Team Victory', 'Saved the world!', 'Cooperative success', 10);

COMMIT TRANSACTION;
