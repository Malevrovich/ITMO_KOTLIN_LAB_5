package data.movie

import data.coordinates.Coordinates
import data.person.Person
import kotlinx.datetime.LocalDateTime

interface MovieBuilder {
    fun clear(): MovieBuilder

    fun setGenre(genre: MovieGenre): MovieBuilder
    fun setName(name: String): MovieBuilder
    fun setScreenwriter(person: Person): MovieBuilder
    fun setOscarsCount(count: Int): MovieBuilder
    fun setUsaBoxOffice(usaBoxOffice: Float): MovieBuilder
    fun setLength(length: Int): MovieBuilder
    fun setCoordinates(coordinates: Coordinates): MovieBuilder
    fun setId(id: Int): MovieBuilder
    fun setCreationDate(time: LocalDateTime): MovieBuilder

    fun setDefault(): MovieBuilder

    fun copyFrom(movie: Movie): MovieBuilder

    fun build(): Movie
    fun buildDefault(id: Int?): Movie
}