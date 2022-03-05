package data.movie

import data.coordinates.CoordinatesBuilder
import data.coordinates.CoordinatesBuilderImpl
import data.coordinates.CoordinatesReader
import data.coordinates.CoordinatesReaderImpl
import data.person.PersonBuilder
import data.person.PersonBuilderImpl
import data.person.PersonReader
import data.person.PersonReaderImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import java.io.*
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

internal class MovieReaderImplTest {

    lateinit var movieReader: MovieReaderImpl

    private val di = DI{
        bindProvider<CoordinatesBuilder> { CoordinatesBuilderImpl() }
        bindProvider<MovieBuilder> { MovieBuilderImpl(di) }
        bindProvider<PersonBuilder> { PersonBuilderImpl() }
        bindProvider<CoordinatesReader> { CoordinatesReaderImpl(di) }
        bindProvider<PersonReader> { PersonReaderImpl(di) }
    }

    @BeforeTest
    fun setup(){
        movieReader = MovieReaderImpl(di)
    }

    private fun byteArrayInputStream(s: String): ByteArrayInputStream{
        return ByteArrayInputStream(s.toByteArray())
    }

    @Test
    fun askName(){
        val movieBuilder: MovieBuilder by di.instance()

        fun test1(){
            val inp = byteArrayInputStream("name")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askName(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals("name", movieBuilder.build().name)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("\n\nname")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askName(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(movieBuilder.build().name, "name")

            assertEquals(3, out.toString().split('\n').size)
        }
        test2()

        fun test3(){
            val inp = byteArrayInputStream("")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            assertFailsWith(EOFException::class){
                movieReader.askName(Scanner(inp), PrintStream(out), movieBuilder)
            }
        }
        test3()
    }

    @Test
    fun askOscarsCount(){
        val movieBuilder: MovieBuilder by di.instance()

        fun test1(){
            val inp = byteArrayInputStream("12")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askOscarsCount(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(movieBuilder.build().oscarsCount, 12)
            assertEquals(1, out.toString().split("\n").size)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("\n\n10")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askOscarsCount(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(movieBuilder.build().oscarsCount, 10)

            assertEquals(2, out.toString().split('\n').size)
        }
        test2()

        fun test3(){
            val inp = byteArrayInputStream("1,423")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            assertFailsWith(EOFException::class) {
                movieReader.askOscarsCount(Scanner(inp), PrintStream(out), movieBuilder)
            }
        }
        test3()

        fun test4(){
            val inp = byteArrayInputStream("1,23\n12")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()

            movieReader.askOscarsCount(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(12, movieBuilder.build().oscarsCount)
            assertEquals(2, out.toString().split("\n").size)
        }
        test4()

        fun test5(){
            val inp = byteArrayInputStream("-10\n10")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()

            movieReader.askOscarsCount(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(10, movieBuilder.build().oscarsCount)
            assertEquals(2, out.toString().split("\n").size)
        }
        test5()
    }

    @Test
    fun askUsaBoxOffice(){
        val movieBuilder: MovieBuilder by di.instance()
        val delta = 0.0001f

        fun test1(){
            val inp = byteArrayInputStream("1,3223")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askUsaBoxOffice(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(1.3223f, movieBuilder.build().usaBoxOffice, delta)
            assertEquals(1, out.toString().split("\n").size)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("\n\n12,34")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askUsaBoxOffice(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(12.34f, movieBuilder.build().usaBoxOffice, delta)
            assertEquals(2, out.toString().split("\n").size)
        }
        test2()

        fun test3(){
            val inp = byteArrayInputStream("one\n12,43")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askUsaBoxOffice(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(12.43f, movieBuilder.build().usaBoxOffice, delta)
            assertEquals(2, out.toString().split("\n").size)
        }
        test3()

        fun test4(){
            val inp = byteArrayInputStream("")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()

            assertFailsWith(EOFException::class){
                movieReader.askUsaBoxOffice(Scanner(inp), PrintStream(out), movieBuilder)
            }
        }
        test4()

        fun test5(){
            val inp = byteArrayInputStream("-123\n13")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()

            movieReader.askUsaBoxOffice(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(13f, movieBuilder.build().usaBoxOffice, delta)
            assertEquals(2, out.toString().split("\n").size)
        }
        test5()
    }

    @Test
    fun askLength(){
        val movieBuilder: MovieBuilder by di.instance()

        fun test1(){
            val inp = byteArrayInputStream("12")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askLength(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(12, movieBuilder.build().length)
            assertEquals(1, out.toString().split("\n").size)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("\n\n12")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askLength(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(12, movieBuilder.build().length)
            assertEquals(2, out.toString().split("\n").size)
        }
        test2()

        fun test3(){
            val inp = byteArrayInputStream("abc\n321")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()
            movieReader.askLength(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(321, movieBuilder.build().length)
            assertEquals(2, out.toString().split("\n").size)
        }
        test3()

        fun test4(){
            val inp = byteArrayInputStream("one")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()

            assertFailsWith(EOFException::class){
                movieReader.askLength(Scanner(inp), PrintStream(out), movieBuilder)
            }
        }
        test4()

        fun test5(){
            val inp = byteArrayInputStream("-12344321\n43434")
            val out = ByteArrayOutputStream()

            movieBuilder.setDefault()

            movieReader.askLength(Scanner(inp), PrintStream(out), movieBuilder)

            assertEquals(43434, movieBuilder.build().length)
            assertEquals(2, out.toString().split("\n").size)
        }
        test5()
    }

    fun askMovie(){
        val inp = byteArrayInputStream(
                 "name\n" +
                    "100\n" +
                    "-100\n" +
                    "10\n" +
                    "1,5\n" +
                    "1\n" +
                    "DRAMA\n" +
                    "somebody\n\n\n")
        val out = ByteArrayOutputStream()

        val movie = movieReader.askMovie(inp, out)

        assertEquals("name", movie.name)
        assertEquals(100, movie.coordinates.x)
        assertEquals(-100, movie.coordinates.y)
        assertEquals(10, movie.oscarsCount)
        assertEquals(1.5f, movie.usaBoxOffice, 0.001f)
        assertEquals(1, movie.length)
        assertEquals(MovieGenre.DRAMA, movie.genre)
        assertEquals("somebody", movie.screenwriter.name)
        assertNull(movie.screenwriter.birthday)
        assertNull(movie.screenwriter.nationality)
    }
}