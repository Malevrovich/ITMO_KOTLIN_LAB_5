package share.data.movie

import data.checkNull
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import share.data.coordinates.Coordinates
import share.data.coordinates.CoordinatesBuilder
import share.data.person.Country
import share.data.person.Person
import share.data.person.PersonBuilder
import share.data.user.User
import share.data.user.UserBuilder
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class MovieBuilderImpl(
    private val personBuilder: PersonBuilder,
    private val coordinatesBuilder: CoordinatesBuilder,
    private val userBuilder: UserBuilder
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
    private var user: User? = null

    override fun clear(): MovieBuilder {
        genre = null
        name = null
        screenwriter = null
        oscarsCount = null
        usaBoxOffice = null
        length = null
        coordinates = null
        user = null

        return this
    }

    override fun setGenre(genre: MovieGenre?): MovieBuilder {
        this.genre = genre ?: return this

        return this
    }

    override fun setName(name: String?): MovieBuilder {
        if(name?.isBlank() == true){
            throw IllegalArgumentException("Поле name не может быть пустым")
        }
        this.name = name ?: return this

        return this
    }

    override fun setScreenwriter(person: Person?): MovieBuilder {
        this.screenwriter = person ?: personBuilder.buildDefault()

        return this
    }

    override fun setOscarsCount(count: Int?): MovieBuilder {
        if (count != null) {
            if(count <= 0){
                throw IllegalArgumentException("Поле oscarsCount должно быть больше нуля")
            }
        }
        this.oscarsCount = count ?: return this

        return this
    }

    override fun setUsaBoxOffice(usaBoxOffice: Float?): MovieBuilder {
        if (usaBoxOffice != null) {
            if(usaBoxOffice <= 0){
                throw IllegalArgumentException("Поле usaBoxOffice должно быть больше нуля")
            }
        }
        this.usaBoxOffice = usaBoxOffice ?: return this

        return this
    }

    override fun setLength(length: Int?): MovieBuilder {
        if (length != null) {
            if(length <= 0){
                throw IllegalArgumentException("Поле length должно быть больше нуля")
            }
        }
        this.length = length ?: return this

        return this
    }

    override fun setCoordinates(coordinates: Coordinates?): MovieBuilder {
        this.coordinates = coordinates ?: return this

        return this
    }

    override fun setCreationDate(time: LocalDateTime?): MovieBuilder {
        this.creationDate = time ?: return this

        return this
    }

    override fun setUser(user: User?): MovieBuilder {
        this.user = user ?: return this

        return this
    }

    private fun toFloatOrIllegalArgument(field: String, value: String): Float{
        return try{
            value.toFloat()
        } catch (e: NumberFormatException){
            throw IllegalArgumentException("Поле $field должно быть числом")
        }
    }

    private fun toIntOrIllegalArgument(field: String, value: String): Int{
        return try{
            value.toInt()
        } catch (e: NumberFormatException){
            throw IllegalArgumentException("Поле $field должно быть целым числом")
        }
    }

    override fun setStrings(
        name: String,
        screenwriter: String,
        oscarsCount: String,
        length: String,
        usaBoxOffice: String,
        genre: String,
        country: String,
        birthday: String,
        x: String,
        y: String
    ): MovieBuilder {
        val coordinates = coordinatesBuilder
            .setX(toFloatOrIllegalArgument("x", x))
            .setY(toIntOrIllegalArgument("y", y))
            .build()

        val format = "HH:mm dd.MM.yyyy"
        val formatter = DateTimeFormatter.ofPattern(format)

        val person = personBuilder
            .setName(screenwriter)
            .setBirthday(
                try{
                    java.time.LocalDateTime.parse(birthday, formatter).toKotlinLocalDateTime()
                } catch (e: DateTimeParseException){
                    throw IllegalArgumentException(e)
                }
            )
            .setNationality(Country.valueOf(country))
            .build()

        setName(name)
        setOscarsCount(toIntOrIllegalArgument("oscarsCount", oscarsCount))
        setLength(toIntOrIllegalArgument("length", length))
        setUsaBoxOffice(toFloatOrIllegalArgument("usaBoxOffice", usaBoxOffice))
        setGenre(MovieGenre.valueOf(genre))
        setCoordinates(coordinates)
        setScreenwriter(person)

        return this
    }

    override fun setProps(props: Properties): MovieBuilder {
        setName(getOrNull(props, "name") as String?)

        setCreationDate(
            (getOrNull(props, "creation_date") as String?)?.let {
                val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                java.time.LocalDateTime.parse(
                    it,
                    pattern
                ).toKotlinLocalDateTime()
            }
        )

        val genre = getOrNull(props, "genre") as String?
        setGenre(genre?.let { MovieGenre.valueOf(it) })

        setLength((getOrNull(props, "length") as String?)?.toInt())
        setOscarsCount((getOrNull(props, "oscars_count") as String?)?.toInt())
        setUsaBoxOffice((getOrNull(props, "usa_box_office") as String?)?.toFloat())

        setId(getOrNull(props, "id") as Int?)

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
        user = userBuilder.buildDefault()

        return this
    }

    override fun setId(id: Int?): MovieBuilder {
        if (id != null) {
            if(id <= 0){
                throw IllegalArgumentException("Поле id должно быть больше нуля")
            }
        }

        this.id = id ?: return this

        return this
    }

    override fun copyFrom(el: Movie?): MovieBuilder {
        if(el == null) {
            return this
        }

        setGenre(el.genre)
        setName(el.name)
        setScreenwriter(el.screenwriter.copy())
        setId(el.id)
        setLength(el.length)
        setCoordinates(el.coordinates.copy())
        setOscarsCount(el.oscarsCount)
        setUsaBoxOffice(el.usaBoxOffice)
        setCreationDate(el.creationDate)
        setUser(el.user)

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
        checkNull(user, "user")

        val res = Movie(id?: -1,
            creationDate?:Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            genre!!, name!!, screenwriter!!, oscarsCount!!, usaBoxOffice!!,
            length!!, coordinates!!, user!!)

        clear()

        return res
    }

    override fun buildDefault(): Movie {
        clear()

        setDefault()

        val res = Movie(-1,
            creationDate?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            genre!!, name!!, screenwriter!!, oscarsCount!!, usaBoxOffice!!,
            length!!, coordinates!!, user!!)

        clear()

        return res
    }
}