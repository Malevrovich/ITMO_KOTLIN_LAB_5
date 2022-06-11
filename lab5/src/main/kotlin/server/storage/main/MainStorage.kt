package server.storage.main

import kotlinx.datetime.LocalDateTime
import server.storage.data.Data
import share.data.movie.Movie
import share.data.movie.MovieGenre
import share.data.person.Person
import share.data.user.User

abstract class MainStorage(val initTime: LocalDateTime) {
    abstract fun getAll(): List<Movie>

    abstract fun add(movie: Movie): Boolean

    abstract fun getData(): Data

    abstract fun update(id: Int, el: Movie, user: User)

    abstract fun remove(id: Int, user: User): Boolean

    abstract fun clear(user: User)

    abstract fun addIfMin(movie: Movie): Boolean

    abstract fun removeGreater(movie: Movie, user: User): Int

    abstract fun sumOfLength(): Int

    abstract fun getUniqueGenre(): List<MovieGenre>

    abstract fun getDescendingScreenwriters(): List<Person>

    abstract fun getSize(): Int

    abstract fun isEmpty(): Boolean

    abstract fun get(id: Int): Movie
}