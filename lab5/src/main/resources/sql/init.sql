CREATE TYPE MovieGenre AS ENUM ('WESTERN', 'DRAMA', 'TRAGEDY');
CREATE TYPE Country AS ENUM ('USA', 'SOUTH_KOREA', 'NORTH_KOREA', 'JAPAN');

CREATE TABLE users (
                       uid serial PRIMARY KEY,
                       login varchar(40) UNIQUE NOT NULL,
                       password bytea NOT NULL,
                       salt char(5) NOT NULL
);

CREATE SEQUENCE id_seq START 1;

BEGIN;

CREATE TABLE movie(
                    movie_id integer PRIMARY KEY DEFAULT nextval('id_seq'),
                    name varchar(100) NOT NULL, CHECK(name <> ''),
                    creation_date timestamptz NOT NULL DEFAULT NOW(),
                    oscars_count integer NOT NULL, CHECK(oscars_count > 0),
                    usa_box_office real NOT NULL, CHECK(usa_box_office > 0),
                    length integer NOT NULL, CHECK(length > 0),
                    genre MovieGenre NOT NULL,
                    uid integer NOT NULL REFERENCES users (uid)
);

CREATE TABLE coordinates(
                    movie_id integer PRIMARY KEY DEFAULT nextval('id_seq'),
                    x real NOT NULL, CHECK(x > -330),
                    y integer NOT NULL
);

CREATE TABLE persons(
                    movie_id integer PRIMARY KEY DEFAULT nextval('id_seq'),
                    name varchar(100) NOT NULL, CHECK(name <> ''),
                    birthday timestamptz,
                    nationality Country
);

ALTER TABLE movie
    ADD FOREIGN KEY (movie_id) REFERENCES coordinates (movie_id)
        DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE coordinates
    ADD FOREIGN KEY (movie_id) REFERENCES persons (movie_id)
        DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE persons
    ADD FOREIGN KEY (movie_id) REFERENCES movie (movie_id)
        DEFERRABLE INITIALLY DEFERRED;

COMMIT;