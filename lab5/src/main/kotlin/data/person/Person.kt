package data.person

import data.movie.Movie
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Person(val name: String, val birthday: LocalDateTime?, val nationality: Country?){
    fun compareTo(other: Movie): Int {
        if(this.name < other.name){
            return -1
        } else if(this.name > other.name){
            return 1
        }
        return 0
    }
}