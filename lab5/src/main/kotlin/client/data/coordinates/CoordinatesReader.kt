package client.data.coordinates

import share.data.coordinates.Coordinates
import share.data.coordinates.CoordinatesBuilder
import share.io.input.Input
import share.io.output.Output

interface CoordinatesReader {
    fun askCoordinates(inp: Input, out: Output): Coordinates

    fun askX(inp: Input, out: Output, coordinatesBuilder: CoordinatesBuilder)

    fun askY(inp: Input, out: Output, coordinatesBuilder: CoordinatesBuilder)
}