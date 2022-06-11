package server.storage.data

import share.data.movie.Movie

interface Data {
    fun add(movie: Movie)
    fun remove(id: Int)
    fun update(id: Int, movie: Movie)
    fun get(id: Int): Movie?

    fun setData(list: List<Movie>)

    fun getSet(): MutableSet<Movie>
    fun getList(): MutableList<Movie>
}