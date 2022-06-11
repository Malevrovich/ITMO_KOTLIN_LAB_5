package share.data.coordinates

import data.checkNull
import share.data.Builder
import java.util.*


class CoordinatesBuilderImpl: CoordinatesBuilder {
    private var x: Float? = null
    private var y: Int? = null

    override fun clear(): CoordinatesBuilder {
        x = null
        y = null

        return this
    }

    override fun setX(x: Float?): CoordinatesBuilder {
        if (x != null) {
            if(x <= -330){
                throw IllegalArgumentException("Значение поля x должно быть больше -330")
            }
        }
        this.x = x ?: return this

        return this
    }

    override fun setY(y: Int?): CoordinatesBuilder {
        this.y = y ?: return this

        return this
    }

    override fun setProps(props: Properties): Builder<Coordinates> {
        setX((getOrNull(props, "x") as String?)?.toFloat())
        setY((getOrNull(props, "y") as String?)?.toInt())

        return this
    }

    override fun setDefault(): CoordinatesBuilder {
        x = 1f
        y = 1

        return this
    }

    override fun copyFrom(el: Coordinates?): CoordinatesBuilder {
        setX(el?.x)
        setY(el?.y)

        return this
    }

    override fun build(): Coordinates {
        checkNull(x, "x")
        checkNull(y, "y")

        val res = Coordinates(x!!, y!!)

        clear()

        return res
    }

    override fun buildDefault(): Coordinates {
        clear()
        setDefault()

        val res = Coordinates(x!!, y!!)

        clear()

        return res
    }
}