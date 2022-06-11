package client.data.movie

import client.auth.LoginManager
import client.data.coordinates.CoordinatesReader
import client.data.person.PersonReader
import share.data.movie.Movie
import share.data.movie.MovieBuilder
import share.io.input.Input
import share.io.output.Output

class MovieReaderImpl(
    private val coordinatesReader: CoordinatesReader,
    private val personReader: PersonReader,
    private val movieBuilder: MovieBuilder,
    private val loginManager: LoginManager
) : MovieReader {

    fun askName(inp: Input, out: Output, movieBuilder: MovieBuilder){

        while(true){
            out.print("Введите поле name: ")

            try{
                movieBuilder.setName(inp.nextLine())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            }

            break
        }
    }

    override fun askOscarsCount(inp: Input, out: Output, movieBuilder: MovieBuilder){

        while(true){
            out.print("Введите поле oscarsCount: ")

            try{
                movieBuilder.setOscarsCount(inp.nextLine().toInt())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: NumberFormatException){
//                out.println(intFormatErrorMsg("oscarsCount"))
            }
            break
        }

    }

    override fun askUsaBoxOffice(inp: Input, out: Output, movieBuilder: MovieBuilder){

        while(true){
            out.print("Введите поле usaBoxOffice: ")

            try{
                movieBuilder.setUsaBoxOffice(inp.nextLine().toFloat())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: NumberFormatException) {
//                out.println(floatFormatError("usaBoxOffice"))
            }

            break
        }

    }

    override fun askLength(inp: Input, out: Output, movieBuilder: MovieBuilder){
        while(true){
            out.print("Введите поле length: ")

            try {
                movieBuilder.setLength(inp.nextLine().toInt())
            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            } catch (e: NumberFormatException){
//                out.println(intFormatErrorMsg("length"))
            }
            break
        }
    }

    override fun askMovie(inp: Input, out: Output): Movie {
        askName(inp, out, movieBuilder)

        movieBuilder.setCoordinates(coordinatesReader.askCoordinates(inp, out))

        askOscarsCount(inp, out, movieBuilder)

        askUsaBoxOffice(inp, out, movieBuilder)

        askLength(inp, out, movieBuilder)

//        movieBuilder.setGenre(askEnum<MovieGenre>(inp, out, "genre")!!)

        movieBuilder.setScreenwriter(personReader.askPerson(inp, out))

        movieBuilder.setUser(loginManager.getCurrentSession())

        return movieBuilder.build()
    }
}