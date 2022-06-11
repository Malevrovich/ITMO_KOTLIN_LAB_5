package share.data.movie

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import share.data.coordinates.Coordinates
import share.data.person.Person
import share.data.user.User


@Serializable
data class Movie(var id: Int,
                 var creationDate: LocalDateTime,
                 var genre: MovieGenre,
                 var name: String,
                 var screenwriter: Person,
                 var oscarsCount: Int,
                 var usaBoxOffice: Float,
                 var length: Int,
                 var coordinates: Coordinates,
                 var user: User
): Comparable<Movie>
{

    override fun hashCode(): Int {
        return id
    }

    override fun compareTo(other: Movie): Int {
        return this.id - other.id
    }

    fun setProperties(movie: Movie): Movie {
        this.id = movie.id
        this.creationDate = movie.creationDate
        this.genre = movie.genre
        this.name = movie.name
        this.screenwriter = movie.screenwriter
        this.oscarsCount = movie.oscarsCount
        this.usaBoxOffice = movie.usaBoxOffice
        this.length = movie.length
        this.coordinates = movie.coordinates
        this.user = movie.user

        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (creationDate != other.creationDate) return false
        if (genre != other.genre) return false
        if (name != other.name) return false
        if (screenwriter != other.screenwriter) return false
        if (oscarsCount != other.oscarsCount) return false
        if (usaBoxOffice != other.usaBoxOffice) return false
        if (length != other.length) return false
        if (coordinates != other.coordinates) return false
        if (user != other.user) return false

        return true
    }
}
