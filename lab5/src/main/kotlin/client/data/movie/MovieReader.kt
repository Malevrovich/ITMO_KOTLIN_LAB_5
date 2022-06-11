package client.data.movie

import share.data.movie.Movie
import share.data.movie.MovieBuilder
import share.io.input.Input
import share.io.output.Output

interface MovieReader {
    fun askMovie(inp: Input, out: Output): Movie

    fun askOscarsCount(inp: Input, out: Output, movieBuilder: MovieBuilder)

    fun askUsaBoxOffice(inp: Input, out: Output, movieBuilder: MovieBuilder)

    fun askLength(inp: Input, out: Output, movieBuilder: MovieBuilder)
}