package client.data.movie

import server.data.IdGenerator
import data.checkNull
import share.data.coordinates.Coordinates
import client.data.coordinates.CoordinatesBuilder
import share.data.movie.Movie
import share.data.movie.MovieGenre
import share.data.person.Person
import client.data.person.PersonBuilder
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MovieBuilderImpl(
    private val idGenerator: IdGenerator,
    private val personBuilder: PersonBuilder,
    private val coordinatesBuilder: CoordinatesBuilder
): MovieBuilder {

    private var genre: MovieGenre? = null
    private var name: String? = null
    private var screenwriter: Person? = null
    private var oscarsCount: Int? = null
    private var usaBoxOffice: Float? = null
    private var length: Int? = null
    private var coordinates: Coordinates? = null
    private var creationDate: LocalDateTime? = null
    private var id: Int? = null

    override fun clear(): MovieBuilder {
        genre = null
        name = null
        screenwriter = null
        oscarsCount = null
        usaBoxOffice = null
        length = null
        coordinates = null

        return this
    }

    override fun setGenre(genre: MovieGenre): MovieBuilder {
        this.genre = genre

        return this
    }

    override fun setName(name: String): MovieBuilder {
        if(name.isEmpty()){
            throw IllegalArgumentException("Поле name не может быть пустым")
        }
        this.name = name

        return this
    }

    override fun setScreenwriter(person: Person): MovieBuilder {
        this.screenwriter = person

        return this
    }

    override fun setOscarsCount(count: Int): MovieBuilder {
        if(count <= 0){
            throw IllegalArgumentException("Поле oscarsCount должно быть больше нуля")
        }
        this.oscarsCount = count

        return this
    }

    override fun setUsaBoxOffice(usaBoxOffice: Float): MovieBuilder {
        if(usaBoxOffice <= 0){
            throw IllegalArgumentException("Поле usaBoxOffice должно быть больше нуля")
        }
        this.usaBoxOffice = usaBoxOffice

        return this
    }

    override fun setLength(length: Int): MovieBuilder {
        if(length <= 0){
            throw IllegalArgumentException("Поле length должно быть больше нуля")
        }
        this.length = length

        return this
    }

    override fun setCoordinates(coordinates: Coordinates): MovieBuilder {
        this.coordinates = coordinates

        return this
    }

    override fun setCreationDate(time: LocalDateTime): MovieBuilder {
        this.creationDate = time

        return this
    }

    override fun setDefault(): MovieBuilder {
        genre = MovieGenre.DRAMA
        name = "untitled"
        screenwriter = personBuilder.buildDefault()
        oscarsCount = 1
        usaBoxOffice = 1f
        length = 1
        coordinates = coordinatesBuilder.buildDefault()

        return this
    }

    override fun setId(id: Int): MovieBuilder {
        if(id <= 0){
            throw IllegalArgumentException("Поле id должно быть больше нуля")
        }

        this.id = id

        return this
    }

    override fun copyFrom(movie: Movie): MovieBuilder {
        with(movie){
            setGenre(genre)
            setName(name)
            setScreenwriter(screenwriter.copy())
            setId(id)
            setLength(length)
            setCoordinates(coordinates.copy())
            setOscarsCount(oscarsCount)
            setUsaBoxOffice(usaBoxOffice)
            setCreationDate(creationDate)
        }

        return this
    }

    override fun build(): Movie {
        checkNull(genre, "genre")
        checkNull(name, "name")
        checkNull(screenwriter, "screenwriter")
        checkNull(oscarsCount, "oscarsCount")
        checkNull(usaBoxOffice, "usaBoxOffice")
        checkNull(length, "length")
        checkNull(coordinates, "coordinates")

        val res = Movie(id?: idGenerator.generate(),
            creationDate?:Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            genre!!, name!!, screenwriter!!, oscarsCount!!, usaBoxOffice!!,
            length!!, coordinates!!)

        clear()

        return res
    }

    override fun buildDefault(id: Int?): Movie {
        clear()

        setDefault()
        setId(id ?: idGenerator.generate())

        val res = Movie(id?: idGenerator.generate(),
            creationDate?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            genre!!, name!!, screenwriter!!, oscarsCount!!, usaBoxOffice!!,
            length!!, coordinates!!)

        clear()

        return res
    }
}