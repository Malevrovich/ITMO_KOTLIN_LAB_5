package commands

import commands.util.CommandResult
import data.movie.Movie
import storage.Storage

class AddCmd(movie: Movie, storage: Storage) : OneArgumentStorageCmd(movie, storage, "add") {
    override fun execute(): CommandResult {
        if(storage.add(movie)) {
            return CommandResult(false, "Элемент был успешно добавлен")
        }
        return CommandResult(false, "Элемент с таким id уже существует, используйте update")
    }
}