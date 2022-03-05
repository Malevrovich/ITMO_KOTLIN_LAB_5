package commands

import commands.util.CommandResult
import data.movie.Movie
import storage.Storage

class RemoveGreaterCmd(movie: Movie, storage: Storage)
    : OneArgumentStorageCmd(movie, storage, "remove_greater") {
    override fun execute(): CommandResult {
        val res = storage.removeGreater(movie)
        return CommandResult(false, "Было удалено $res элементов")
    }
}