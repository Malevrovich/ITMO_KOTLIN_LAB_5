package client.data.coordinates

import share.data.coordinates.Coordinates

interface CoordinatesBuilder {
    fun clear(): CoordinatesBuilder

    fun setX(x: Float): CoordinatesBuilder
    fun setY(y: Int): CoordinatesBuilder

    fun setDefault(): CoordinatesBuilder

    fun build(): Coordinates
    fun buildDefault(): Coordinates
}