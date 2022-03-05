package data.movie

import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

interface MovieReader {
    fun askMovie(inp: Scanner, out: PrintStream): Movie

    fun askOscarsCount(inp: Scanner, out: PrintStream, movieBuilder: MovieBuilder)

    fun askUsaBoxOffice(inp: Scanner, out: PrintStream, movieBuilder: MovieBuilder)

    fun askLength(inp: Scanner, out: PrintStream, movieBuilder: MovieBuilder)
}