package data.coordinates

import data.checkFloat
import data.checkInt
import org.kodein.di.DI
import org.kodein.di.instance
import util.ParseException
import java.io.EOFException
import java.io.PrintStream
import java.util.*

class CoordinatesReaderImpl(
    private val coordinatesBuilder: CoordinatesBuilder
): CoordinatesReader {

    override fun askX(inp: Scanner, out: PrintStream, coordinatesBuilder: CoordinatesBuilder) {
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true) {
            out.print("Введите поле Coordinate.x: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            try{
                checkFloat(inp, "Coordinate.x")
                coordinatesBuilder.setX(inp.nextFloat())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: ParseException){
                out.println(e.message)
                inp.nextLine()
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

            try {
                checkInt(inp, "Coordinates.y")
                coordinatesBuilder.setY(inp.nextInt())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: ParseException){
                out.println(e.message)
                inp.nextLine()
                continue
            }
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