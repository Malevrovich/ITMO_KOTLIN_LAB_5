package share.commands.dto

import kotlinx.serialization.Serializable
import share.data.movie.Movie
import share.data.user.User

@Serializable
data class CommandDTO(val type: CommandType,
                      val user: User,
                      val movie_args: List<Movie> = emptyList(),
                      val int_args: List<Int> = emptyList(),
                      val string_args: List<String> = emptyList())