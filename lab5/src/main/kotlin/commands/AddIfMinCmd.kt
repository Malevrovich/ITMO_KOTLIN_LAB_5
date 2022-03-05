package commands

import commands.util.CommandResult
import data.movie.Movie
import storage.Storage

class AddIfMinCmd(movie: Movie, storage: Storage) : OneArgumentStorageCmd(movie, storage, "add_if_min") {
    override fun execute(): CommandResult {
        if(storage.addIfMin(movie)){
            return CommandResult(false, "Элемент был успешно добавлен")
        }
        return CommandResult(false, "Элемент не был добавлен")
    }
}