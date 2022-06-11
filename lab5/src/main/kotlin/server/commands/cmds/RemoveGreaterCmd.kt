package server.commands.cmds

import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.data.movie.Movie
import share.data.user.User

class RemoveGreaterCmd(movie: Movie, storage: MainStorage, user: User)
    : OneArgumentStorageCmd(movie, storage, "remove_greater", user, CommandType.REMOVE_GREATER) {
    override fun execute(): CommandResult {
        val res = storage.removeGreater(movie, user)
        return CommandResult(false, "Было удалено $res элементов")
    }
}