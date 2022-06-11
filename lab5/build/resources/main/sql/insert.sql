WITH ids AS (
    INSERT INTO movie
        (name, oscars_count, usa_box_office, length, genre, uid)
        VALUES
            ('name', 10, 10, 10, 'DRAMA', 1)
        RETURNING movie_id
), _ AS (
    INSERT INTO coordinates
        (movie_id, x, y)
        VALUES
            ((SELECT movie_id FROM ids), 100, 100)
)
INSERT INTO persons
(movie_id, name)
VALUES((SELECT movie_id FROM ids), 'john');