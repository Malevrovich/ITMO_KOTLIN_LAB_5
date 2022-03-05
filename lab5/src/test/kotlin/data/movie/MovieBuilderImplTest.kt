package data.movie

import data.coordinates.Coordinates
import data.coordinates.CoordinatesBuilder
import data.coordinates.CoordinatesBuilderImpl
import data.person.Person
import data.person.PersonBuilder
import data.person.PersonBuilderImpl
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bindProvider
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

internal class MovieBuilderImplTest {

    private val di = DI{
        bindProvider<CoordinatesBuilder> { CoordinatesBuilderImpl() }
        bindProvider<PersonBuilder> { PersonBuilderImpl() }
    }

    lateinit var movieBuilder: MovieBuilder

    private fun checkFields(movie: Movie, res: Movie){
        assertEquals(movie.id, res.id)
        assertEquals(movie.coordinates, res.coordinates)
        assertEquals(movie.name, res.name)
        assertEquals(movie.genre, res.genre)
        assertEquals(movie.length, res.length)
        assertEquals(movie.creationDate, res.creationDate)
        assertEquals(movie.oscarsCount, res.oscarsCount)
        assertEquals(movie.usaBoxOffice, res.usaBoxOffice)
        assertEquals(movie.screenwriter, res.screenwriter)
    }

    @BeforeTest
    fun setup(){
        movieBuilder = MovieBuilderImpl(di)
    }

    @Test
    fun setName() {
        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setName("")
        }
    }

    @Test
    fun setOscarsCount() {
        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setOscarsCount(-1)
        }

        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setOscarsCount(0)
        }
    }

    @Test
    fun setUsaBoxOffice() {
        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setUsaBoxOffice(-1.4f)
        }

        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setUsaBoxOffice(0f)
        }
    }

    @Test
    fun setLength() {
        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setLength(-1)
        }

        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setLength(0)
        }
    }

    @Test
    fun setId() {
        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setId(-1)
        }

        assertFailsWith(IllegalArgumentException::class){
            movieBuilder.setId(0)
        }
    }

    @Test
    fun copyFrom() {
        val movie = Movie(1,
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            MovieGenre.DRAMA, "film",
            Person("name", null, null), 1, 1f, 1,
            Coordinates(1f, 1)
        )

        fun test1()
        {
            movieBuilder.copyFrom(movie)

            val res = movieBuilder.build()

            checkFields(movie, res)

            assertFalse(movie.screenwriter === res.screenwriter)
            assertFalse(movie.coordinates === res.coordinates)
        }
        test1()

        fun test2() {
            movieBuilder.clear()
            movieBuilder.copyFrom(movie)
            movieBuilder.setId(2)

            val res = movieBuilder.build()

            assertNotEquals(movie.id, res.id)

            assertEquals(movie.coordinates, res.coordinates)
            assertEquals(movie.name, res.name)
            assertEquals(movie.genre, res.genre)
            assertEquals(movie.length, res.length)
            assertEquals(movie.creationDate, res.creationDate)
            assertEquals(movie.oscarsCount, res.oscarsCount)
            assertEquals(movie.usaBoxOffice, res.usaBoxOffice)
            assertEquals(movie.screenwriter, res.screenwriter)
        }
        test2()
    }

    @Test
    fun build() {
        val id = 1
        val name = "name"
        val coordinates = Coordinates(1f, 1)
        val oscarsCount = 1
        val usaBoxOffice = 1.5f
        val screenwriter = Person("name",
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()), null)
        val length = 1
        val genre = MovieGenre.TRAGEDY
        val creationDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        movieBuilder.setId(id)
        movieBuilder.setGenre(genre)
        movieBuilder.setName(name)
        movieBuilder.setCoordinates(coordinates)
        movieBuilder.setScreenwriter(screenwriter)
        movieBuilder.setLength(length)
        movieBuilder.setUsaBoxOffice(usaBoxOffice)
        movieBuilder.setOscarsCount(oscarsCount)
        movieBuilder.setCreationDate(creationDateTime)

        val res = movieBuilder.build()

        checkFields(Movie(id, creationDateTime, genre, name, screenwriter, oscarsCount,
                            usaBoxOffice, length, coordinates), res)
    }

    @Test
    fun buildDefault() {
        val movie = Movie(1, Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()), MovieGenre.TRAGEDY, "name",
                            Person("a", null, null), 1, 1f, 1,
                            Coordinates(1f, 1))

        assertEquals(movieBuilder.buildDefault(1), movie)

        assertNotEquals(movieBuilder.buildDefault(2), movieBuilder.buildDefault(1))

        assertEquals(movieBuilder.buildDefault(3).id, 3)
    }
}