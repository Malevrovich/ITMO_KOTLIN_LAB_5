package data.coordinates

interface CoordinatesBuilder {
    fun clear(): CoordinatesBuilder

    fun setX(x: Float): CoordinatesBuilder
    fun setY(y: Int): CoordinatesBuilder

    fun setDefault(): CoordinatesBuilder

    fun build(): Coordinates
    fun buildDefault(): Coordinates
}