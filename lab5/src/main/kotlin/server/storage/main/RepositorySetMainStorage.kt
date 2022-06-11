package server.storage.main

import org.slf4j.LoggerFactory
import server.database.repository.Repository
import server.storage.data.Data
import share.data.movie.Movie
import share.data.user.User
import java.sql.SQLException

class RepositorySetMainStorage(private val repository: Repository,
                               source: Data
): SetMainStorage(source) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun add(movie: Movie): Boolean {
        return repository.insert(movie)
    }

    override fun update(id: Int, el: Movie, user: User) {
        repository.update(id, el, user)
    }

    override fun remove(id: Int, user: User): Boolean {
        if (repository.removeBy(id, user) == 1) {
            return true
        }

        return false
    }

    private val clearSQL = """
        WITH ids AS (SELECT movie_id FROM movie INNER JOIN users USING(uid) WHERE login=?),
        c AS (DELETE FROM coordinates WHERE movie_id in (SELECT * FROM ids)),
        p AS (DELETE FROM persons WHERE movie_id in (SELECT * FROM ids))
        DELETE FROM movie WHERE movie_id in (SELECT * FROM ids);
    """.trimIndent()
    override fun clear(user: User) {
        val stmt = repository.prepareStatement(clearSQL)

        stmt.setString(1, user.login)

        logger.info("Executing clearQuery")

        try {
            stmt.execute()
        } catch (e: SQLException){
            logger.error(e.message)
            throw e
        }

        stmt.close()
    }

    override fun addIfMin(movie: Movie): Boolean {
        if(data.none { it < movie }){
            this.add(movie)
            return true
        }
        return false
    }

    private val removeGreaterSQL =
        """ WITH ids AS (
                DELETE FROM movie
                     WHERE movie_id > ? and uid IN (SELECT uid FROM users WHERE login=?)
                     RETURNING movie_id
                ), delete_coordinates AS (
                DELETE FROM coordinates
                     WHERE movie_id IN (SELECT movie_id FROM ids)
                )
            DELETE FROM persons WHERE movie_id IN (SELECT movie_id FROM ids)
        """.trimIndent()
    override fun removeGreater(movie: Movie, user: User): Int {
        val stmt = repository.prepareStatement(removeGreaterSQL)

        stmt.setInt(1, movie.id)
        stmt.setString(2, user.login)

        logger.info("Executing removeGreaterQuery")
        try {
            return stmt.executeUpdate()
        } catch (e: SQLException) {
            logger.error(e.message)
            throw e
        }
    }
}