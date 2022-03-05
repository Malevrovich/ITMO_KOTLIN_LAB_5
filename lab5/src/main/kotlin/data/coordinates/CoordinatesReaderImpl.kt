package data.coordinates

import data.checkFloat
import data.checkInt
import org.kodein.di.DI
import org.kodein.di.instance
import java.io.EOFException
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

class CoordinatesReaderImpl(di: DI): CoordinatesReader {
    private val coordinatesBuilder: CoordinatesBuilder by di.instance()

    override fun askX(inp: Scanner, out: PrintStream, coordinatesBuilder: CoordinatesBuilder) {
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true) {
            out.print("Введите поле Coordinate.x: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            if(!checkFloat(inp, out, "Coordinate.x")) continue

            try{
                coordinatesBuilder.setX(inp.nextFloat())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            }

            break
        }

        inp.useDelimiter(del)
    }

    override fun askY(inp: Scanner, out: PrintStream, coordinatesBuilder: CoordinatesBuilder) {
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true){
            out.print("Введите поле Coordinates.y: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            if(!checkInt(inp, out, "Coordinates.y")) continue

            coordinatesBuilder.setY(inp.nextInt())
            break
        }

        inp.useDelimiter(del)
    }

    override fun askCoordinates(inp: Scanner, out: PrintStream): Coordinates {
        askX(inp, out, coordinatesBuilder)

        askY(inp, out, coordinatesBuilder)

        return coordinatesBuilder.build()
    }
}