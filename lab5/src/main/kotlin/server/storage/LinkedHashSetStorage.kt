package server.storage

import share.data.movie.Movie
import share.data.movie.MovieGenre
import share.data.person.Person
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.streams.toList

class LinkedHashSetStorage : Storage(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())), Iterable<Movie> {

    private val data: LinkedHashSet<Movie> = LinkedHashSet()

    override fun getAll(): List<Movie> {
        data.toHashSet().sortedBy { it.name }
        return data.sortedBy { it.name }.toList()

    }

    override fun setData(data: List<Movie>){
        this.data.clear()
        this.data.addAll(data)
    }

    override fun add(movie: Movie) = data.add(movie)

    override fun update(id: Int, movie: Movie) {
        try {
            data.first { it.id == id }.setProperties(movie).id = id
        } catch (e: NoSuchElementException){
            throw NoSuchElementException("Элемент с id = $id не найден")
        }
    }

    override fun remove(id: Int) = data.removeIf { it.id == id }

    override fun clear() = data.clear()

    override fun addIfMin(movie: Movie): Boolean {
        if(data.none { it < movie }){
            data.add(movie)
            return true
        }
        return false
    }

    override fun removeGreater(movie: Movie): Int {
        val prev = data.size
        data.removeIf{ it > movie }
        return prev - data.size
    }

    override fun sumOfLength(): Int {
        return data.sumOf { it.length }
    }

    override fun getUniqueGenre(): List<MovieGenre> {
        return data.map { it.genre }.toHashSet().toList()
    }

    override fun getDescendingScreenwriters(): List<Person> {
        return data.map { it.screenwriter }.toList().sortedByDescending { it.name }
    }

    override fun getSize(): Int = data.size

    override fun isEmpty(): Boolean = data.isEmpty()

    override fun iterator(): Iterator<Movie> = data.iterator()

}