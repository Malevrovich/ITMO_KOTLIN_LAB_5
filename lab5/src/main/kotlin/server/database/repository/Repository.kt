package server.database.repository

import server.storage.data.Data
import share.data.movie.Movie
import share.data.user.User
import java.sql.PreparedStatement

interface Repository {
    fun getAll(): List<Movie>

    fun getBy(id: Int): Movie

    fun removeBy(id: Int, user: User): Int

    fun update(id: Int, movie: Movie, user: User)

    fun insert(movie: Movie): Boolean

    fun prepareStatement(req: String): PreparedStatement

    fun closeConnection(statement: PreparedStatement)

    fun synchronize(data: Data)
}