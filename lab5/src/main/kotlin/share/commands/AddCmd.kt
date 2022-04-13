package share.commands

import share.commands.util.CommandResult
import share.data.movie.Movie
import server.storage.Storage
import share.commands.util.CommandType

class AddCmd(movie: Movie, storage: Storage)
    : OneArgumentStorageCmd(movie, storage, "add", CommandType.ADD) {
    override fun execute(): CommandResult {
        if(storage.add(movie)) {
            return CommandResult(false, "Элемент был успешно добавлен")
        }
        return CommandResult(false, "Элемент с таким id уже существует, используйте update")
    }
}