package server.storage

import share.data.movie.Movie
import share.data.movie.MovieGenre
import share.data.person.Person
import kotlinx.datetime.LocalDateTime

abstract class Storage(val initTime: LocalDateTime) {
    abstract fun getAll(): List<Movie>

    abstract fun add(movie: Movie): Boolean

    abstract fun setData(data: List<Movie>)

    abstract fun update(id: Int, movie: Movie)

    abstract fun remove(id: Int): Boolean

    abstract fun clear()

    abstract fun addIfMin(movie: Movie): Boolean

    abstract fun removeGreater(movie: Movie): Int

    abstract fun sumOfLength(): Int

    abstract fun getUniqueGenre(): List<MovieGenre>

    abstract fun getDescendingScreenwriters(): List<Person>

    abstract fun getSize(): Int

    abstract fun isEmpty(): Boolean
}