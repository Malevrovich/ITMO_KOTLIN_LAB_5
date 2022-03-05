package storage

import data.movie.Movie
import data.movie.MovieBuilder
import data.movie.MovieGenre
import data.person.Person
import org.kodein.di.DI
import org.kodein.di.instance
import java.time.LocalDateTime

class LinkedHashSetStorage(di: DI): Storage(LocalDateTime.now()), Iterable<Movie> {
    private val movieBuilder: MovieBuilder by di.instance()

    private val data: LinkedHashSet<Movie> = LinkedHashSet()

    override fun getAll(): List<Movie> {
        return data.toList()
    }

    override fun setData(data: List<Movie>){
        this.data.clear()
        this.data.addAll(data)
    }

    override fun add(movie: Movie) = data.add(movie)

    override fun update(id: Int, movie: Movie) {
        val el = movieBuilder.buildDefault(id)
        if(!data.contains(el)){
            throw NoSuchElementException("Элемент с id = $id не найден")
        }
        data.remove(el)

        movieBuilder.clear()
        movieBuilder.copyFrom(movie)
        movieBuilder.setId(id)

        data.add(movieBuilder.build())
    }

    override fun remove(id: Int) {
        data.remove(movieBuilder.buildDefault(id))
    }

    override fun clear() = data.clear()

    override fun addIfMin(movie: Movie): Boolean {
        if(movie.id < ((data.minByOrNull { it.id })?.id ?: Int.MAX_VALUE)){
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
        var sum = 0
        for(i in data){
            sum += i.length
        }
        return sum
    }

    override fun getUniqueGenre(): List<MovieGenre> {
        val dst: MutableList<MovieGenre> = mutableListOf()
        data.mapTo(dst) { it.genre }
        return dst.toHashSet().toList()
    }

    override fun getDescendingScreenwriters(): List<Person> {
        val dst: MutableList<Person> = mutableListOf()
        data.mapTo(dst) { it.screenwriter }
        return dst.sortedByDescending { it.name }
    }

    override fun getSize(): Int = data.size

    override fun isEmpty(): Boolean = data.isEmpty()

    override fun iterator(): Iterator<Movie> = data.iterator()

}