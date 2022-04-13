package client.data.coordinates

import data.checkFloat
import data.checkInt
import share.data.coordinates.Coordinates
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter
import share.util.ParseException
import java.io.EOFException

class CoordinatesReaderImpl(
    private val coordinatesBuilder: CoordinatesBuilder
): CoordinatesReader {

    override fun askX(inp: UserReader, out: UserWriter, coordinatesBuilder: CoordinatesBuilder) {
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
    }

    override fun askY(inp: UserReader, out: UserWriter, coordinatesBuilder: CoordinatesBuilder) {
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
    }

    override fun askCoordinates(inp: UserReader, out: UserWriter): Coordinates {
        askX(inp, out, coordinatesBuilder)

        askY(inp, out, coordinatesBuilder)

        return coordinatesBuilder.build()
    }
}