package data.movie

import data.coordinates.Coordinates
import data.person.Person
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class Movie(val id: Int,
                 val creationDate: LocalDateTime,
                 val genre: MovieGenre,
                 val name: String,
                 val screenwriter: Person,
                 val oscarsCount: Int,
                 val usaBoxOffice: Float,
                 val length: Int,
                 val coordinates: Coordinates): Comparable<Movie>
{

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false

        return true
    }

    override fun compareTo(other: Movie): Int {
        return this.id - other.id
    }
}
