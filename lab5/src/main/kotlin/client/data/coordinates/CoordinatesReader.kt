package client.data.coordinates

import share.data.coordinates.Coordinates
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter

interface CoordinatesReader {
    fun askCoordinates(inp: UserReader, out: UserWriter): Coordinates

    fun askX(inp: UserReader, out: UserWriter, coordinatesBuilder: CoordinatesBuilder)

    fun askY(inp: UserReader, out: UserWriter, coordinatesBuilder: CoordinatesBuilder)
}