package server.storage.main

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import server.storage.data.Data
import share.data.movie.Movie
import share.data.movie.MovieGenre
import share.data.person.Person
import share.data.user.User

open class SetMainStorage(private val source: Data) : MainStorage(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())), Iterable<Movie> {

    protected val data: MutableSet<Movie>
    get() {
        return source.getSet()
    }

    override fun getAll(): List<Movie> {
        data.toHashSet().sortedBy { it.name }
        return data.sortedBy { it.name }.toList()
    }

    override fun add(movie: Movie) = data.add(movie)

    override fun getData(): Data = source

    override fun update(id: Int, el: Movie, user: User) {
        val tmp = data.firstOrNull { (it.id == id) }
            ?: throw NoSuchElementException("Элемент с id = $id не найден")

        if(tmp.user != user) {
            throw NoSuchElementException("У вас недостаточно прав для редактирования этого обьекта")
        }

        tmp.setProperties(el).id = id
    }

    override fun remove(id: Int, user: User): Boolean = data.removeIf { (it.id == id) and (it.user == user)}

    override fun clear(user: User) {
        data.removeIf { it.user == user }
    }

    override fun addIfMin(movie: Movie): Boolean {
        if(data.none { it < movie }){
            data.add(movie)
            return true
        }
        return false
    }

    override fun removeGreater(movie: Movie, user: User): Int {
        val prev = data.size
        data.removeIf{ (it > movie) and (it.user == user)}
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

    override fun get(id: Int): Movie {
        return data.firstOrNull { it.id == id } ?: throw IllegalArgumentException("Нет элемента с id == $id")
    }

    override fun iterator(): Iterator<Movie> = data.iterator()

}