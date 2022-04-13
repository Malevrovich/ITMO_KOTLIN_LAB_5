package share.commands.util

import kotlinx.serialization.Serializable
import share.data.movie.Movie

@Serializable
data class CommandDTO(val type: CommandType,
                      val movie_args: List<Movie> = emptyList(),
                      val int_args: List<Int> = emptyList(),
                      val string_args: List<String> = emptyList())