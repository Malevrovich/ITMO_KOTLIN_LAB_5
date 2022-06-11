package server.commands.cmds

import share.data.movie.Movie
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

abstract class OneArgumentStorageCmd(open val movie: Movie,
                                     storage: MainStorage,
                                     override val name: String,
                                     override val user: User,
                                     override val type: CommandType
): StorageCmd(storage, name, user, type)