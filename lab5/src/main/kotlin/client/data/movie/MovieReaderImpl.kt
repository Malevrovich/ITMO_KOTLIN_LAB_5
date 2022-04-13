package client.data.movie

import data.askEnum
import data.checkFloat
import data.checkInt
import client.data.coordinates.CoordinatesReader
import share.data.movie.Movie
import share.data.movie.MovieGenre
import client.data.person.PersonReader
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter
import share.util.ParseException
import java.io.EOFException

class MovieReaderImpl(
    private val coordinatesReader: CoordinatesReader,
    private val personReader: PersonReader,
    private val movieBuilder: MovieBuilder
) : MovieReader {

    fun askName(inp: UserReader, out: UserWriter, movieBuilder: MovieBuilder){

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
    }

    override fun askOscarsCount(inp: UserReader, out: UserWriter, movieBuilder: MovieBuilder){

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

    }

    override fun askUsaBoxOffice(inp: UserReader, out: UserWriter, movieBuilder: MovieBuilder){

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

    }

    override fun askLength(inp: UserReader, out: UserWriter, movieBuilder: MovieBuilder){
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
    }

    override fun askMovie(inp: UserReader, out: UserWriter): Movie {
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