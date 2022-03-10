package data.movie

import data.askEnum
import data.checkFloat
import data.checkInt
import data.coordinates.CoordinatesReader
import data.person.PersonReader
import org.kodein.di.DI
import org.kodein.di.instance
import util.ParseException
import java.io.EOFException
import java.io.PrintStream
import java.util.*

class MovieReaderImpl(
    private val coordinatesReader: CoordinatesReader,
    private val personReader: PersonReader,
    private val movieBuilder: MovieBuilder
) : MovieReader{

    fun askName(inp: Scanner, out: PrintStream, movieBuilder: MovieBuilder){
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true){
            out.print("Введите поле name: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            try{
                movieBuilder.setName(inp.nextLine())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            }

            break
        }

        inp.useDelimiter(del)
    }

    override fun askOscarsCount(inp: Scanner, out: PrintStream, movieBuilder: MovieBuilder){
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true){
            out.print("Введите поле oscarsCount: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            try{
                checkFloat(inp, "oscarsCount")
                movieBuilder.setOscarsCount(inp.nextInt())
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

    override fun askUsaBoxOffice(inp: Scanner, out: PrintStream, movieBuilder: MovieBuilder){
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true){
            out.print("Введите поле usaBoxOffice: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            try{
                checkFloat(inp, "usaBoxOffice")
                movieBuilder.setUsaBoxOffice(inp.nextFloat())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: ParseException) {
                out.println(e.message)
                inp.nextLine()
                continue
            }

            break
        }

        inp.useDelimiter(del)
    }

    override fun askLength(inp: Scanner, out: PrintStream, movieBuilder: MovieBuilder){
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true){
            out.print("Введите поле length: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            try {
                checkInt(inp, "length")
                movieBuilder.setLength(inp.nextInt())
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

    override fun askMovie(inp: Scanner, out: PrintStream): Movie {
        askName(inp, out, movieBuilder)

        movieBuilder.setCoordinates(coordinatesReader.askCoordinates(inp, out))

        askOscarsCount(inp, out, movieBuilder)

        askUsaBoxOffice(inp, out, movieBuilder)

        askLength(inp, out, movieBuilder)

        movieBuilder.setGenre(askEnum<MovieGenre>(inp, out, "genre")!!)

        movieBuilder.setScreenwriter(personReader.askPerson(inp, out))

        return movieBuilder.build()
    }
}