package server.storage.data

import org.slf4j.LoggerFactory
import share.data.movie.Movie
import java.util.*
import kotlin.collections.LinkedHashSet

class LinkedHashSetData: Data {
    val data: MutableSet<Movie> = Collections.synchronizedSet(LinkedHashSet())

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun add(movie: Movie) {
        logger.debug("Adding movie to source")
        data.add(movie)
    }

    override fun remove(id: Int) {
        data.removeIf { it.id == id }
    }

    override fun update(id: Int, movie: Movie) {
        remove(id)
        add(movie)
    }

    override fun get(id: Int): Movie? {
        return data.firstOrNull { it.id == id }
    }

    override fun setData(list: List<Movie>) {
        data.clear()
        data.addAll(list)
    }

    override fun getSet(): MutableSet<Movie> {
        logger.debug("Providing data as set from source, size = {}", data.size)
        return data
    }

    override fun getList(): MutableList<Movie> {
        return data.toMutableList()
    }
}