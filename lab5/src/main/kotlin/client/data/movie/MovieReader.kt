package client.data.movie

import share.data.movie.Movie
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter

interface MovieReader {
    fun askMovie(inp: UserReader, out: UserWriter): Movie

    fun askOscarsCount(inp: UserReader, out: UserWriter, movieBuilder: MovieBuilder)

    fun askUsaBoxOffice(inp: UserReader, out: UserWriter, movieBuilder: MovieBuilder)

    fun askLength(inp: UserReader, out: UserWriter, movieBuilder: MovieBuilder)
}