package data.coordinates

import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

interface CoordinatesReader {
    fun askCoordinates(inp: Scanner, out: PrintStream): Coordinates

    fun askX(inp: Scanner, out: PrintStream, coordinatesBuilder: CoordinatesBuilder)

    fun askY(inp: Scanner, out: PrintStream, coordinatesBuilder: CoordinatesBuilder)
}