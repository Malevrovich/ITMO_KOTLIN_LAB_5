package share.commands

import kotlinx.serialization.Serializable
import share.data.movie.Movie
import server.storage.Storage
import share.commands.util.CommandType

abstract class OneArgumentStorageCmd(open val movie: Movie,
                                     storage: Storage,
                                     override val name: String,
                                     override val type: CommandType): StorageCmd(storage, name, type)