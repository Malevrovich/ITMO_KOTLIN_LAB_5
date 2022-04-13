package share.commands

import share.commands.util.CommandResult
import share.data.movie.Movie
import server.storage.Storage
import share.commands.util.CommandType

class RemoveGreaterCmd(movie: Movie, storage: Storage)
    : OneArgumentStorageCmd(movie, storage, "remove_greater", CommandType.REMOVE_GREATER) {
    override fun execute(): CommandResult {
        val res = storage.removeGreater(movie)
        return CommandResult(false, "Было удалено $res элементов")
    }
}