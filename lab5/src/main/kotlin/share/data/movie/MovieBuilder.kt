package share.data.movie

import kotlinx.datetime.LocalDateTime
import share.data.Builder
import share.data.coordinates.Coordinates
import share.data.person.Person
import share.data.user.User
import java.util.*

interface MovieBuilder: Builder<Movie> {
    override fun clear(): MovieBuilder

    fun setGenre(genre: MovieGenre?): MovieBuilder
    fun setName(name: String?): MovieBuilder
    fun setScreenwriter(person: Person?): MovieBuilder
    fun setOscarsCount(count: Int?): MovieBuilder
    fun setUsaBoxOffice(usaBoxOffice: Float?): MovieBuilder
    fun setLength(length: Int?): MovieBuilder
    fun setCoordinates(coordinates: Coordinates?): MovieBuilder
    fun setId(id: Int?): MovieBuilder
    fun setCreationDate(time: LocalDateTime?): MovieBuilder
    fun setUser(user: User?): MovieBuilder

    fun setStrings(
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
    ): MovieBuilder

    override fun setProps(props: Properties): MovieBuilder
    override fun setDefault(): MovieBuilder

    override fun copyFrom(el: Movie?): MovieBuilder

    override fun build(): Movie
    override fun buildDefault(): Movie
}