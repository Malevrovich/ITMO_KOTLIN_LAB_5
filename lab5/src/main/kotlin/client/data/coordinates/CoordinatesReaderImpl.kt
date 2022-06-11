package client.data.coordinates

import data.floatFormatError
import data.intFormatErrorMsg
import share.data.coordinates.Coordinates
import share.data.coordinates.CoordinatesBuilder
import share.io.input.Input
import share.io.output.Output
import share.localization.Localization

class CoordinatesReaderImpl(
    private val coordinatesBuilder: CoordinatesBuilder,
    private val localization: Localization
): CoordinatesReader {

    override fun askX(inp: Input, out: Output, coordinatesBuilder: CoordinatesBuilder) {
        while(true) {
            out.print(localization.getString("askCoordinatesX"))

            try{
                coordinatesBuilder.setX(inp.nextLine().toFloat())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: NumberFormatException){
                out.println(floatFormatError("Coordinates.x", localization))
            }
            break
        }
    }

    override fun askY(inp: Input, out: Output, coordinatesBuilder: CoordinatesBuilder) {
        while(true){
            out.print("Введите поле Coordinates.y: ")

            try {
                coordinatesBuilder.setY(inp.nextLine().toInt())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: NumberFormatException){
                out.println(intFormatErrorMsg("Coordinates.y", localization))
            }
            break
        }
    }

    override fun askCoordinates(inp: Input, out: Output): Coordinates {
        askX(inp, out, coordinatesBuilder)

        askY(inp, out, coordinatesBuilder)

        return coordinatesBuilder.build()
    }
}