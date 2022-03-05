package storage

import data.movie.Movie
import data.movie.MovieGenre
import data.person.Person
import java.time.LocalDateTime

abstract class Storage(val initTime: LocalDateTime) {
    abstract fun getAll(): List<Movie>

    abstract fun add(movie: Movie): Boolean

    abstract fun setData(data: List<Movie>)

    abstract fun update(id: Int, movie: Movie)

    abstract fun remove(id: Int)

    abstract fun clear()

    abstract fun addIfMin(movie: Movie): Boolean

    abstract fun removeGreater(movie: Movie): Int

    abstract fun sumOfLength(): Int

    abstract fun getUniqueGenre(): List<MovieGenre>

    abstract fun getDescendingScreenwriters(): List<Person>

    abstract fun getSize(): Int

    abstract fun isEmpty(): Boolean
}