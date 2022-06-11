package share.data.coordinates

import share.data.Builder
import java.util.*

interface CoordinatesBuilder: Builder<Coordinates> {
    override fun clear(): CoordinatesBuilder

    fun setX(x: Float?): CoordinatesBuilder
    fun setY(y: Int?): CoordinatesBuilder

    override fun setProps(props: Properties): Builder<Coordinates>
    override fun setDefault(): CoordinatesBuilder

    override fun copyFrom(el: Coordinates?): CoordinatesBuilder

    override fun build(): Coordinates
    override fun buildDefault(): Coordinates
}