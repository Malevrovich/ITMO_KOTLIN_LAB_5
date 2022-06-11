WITH ids AS (
     DELETE FROM movie
                     WHERE movie_id = ?
                     RETURNING movie_id
                    ), delete_coordinates AS (
     DELETE FROM coordinates
                     WHERE movie_id IN (SELECT movie_id FROM ids)
                    )
    DELETE FROM persons WHERE movie_id IN (SELECT movie_id FROM ids)