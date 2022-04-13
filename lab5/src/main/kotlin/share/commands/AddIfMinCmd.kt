package share.commands

import share.commands.util.CommandResult
import share.data.movie.Movie
import server.storage.Storage
import share.commands.util.CommandType

class AddIfMinCmd(movie: Movie, storage: Storage)
    : OneArgumentStorageCmd(movie, storage, "add_if_min", CommandType.ADD_IF_MIN) {
    override fun execute(): CommandResult {
        if(storage.addIfMin(movie)){
            return CommandResult(false, "Элемент был успешно добавлен")
        }
        return CommandResult(false, "Элемент не был добавлен")
    }
}