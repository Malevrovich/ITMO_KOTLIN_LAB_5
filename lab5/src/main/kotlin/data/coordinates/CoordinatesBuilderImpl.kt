package data.coordinates

import data.checkNull


class CoordinatesBuilderImpl: CoordinatesBuilder {
    private var x: Float? = null
    private var y: Int? = null

    override fun clear(): CoordinatesBuilder {
        x = null
        y = null

        return this
    }

    override fun setX(x: Float): CoordinatesBuilder {
        if(x <= -330){
            throw IllegalArgumentException("Значение поля x должно быть больше -330")
        }
        this.x = x

        return this
    }

    override fun setY(y: Int): CoordinatesBuilder {
        this.y = y

        return this
    }

    override fun setDefault(): CoordinatesBuilder {
        x = 1f
        y = 1

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