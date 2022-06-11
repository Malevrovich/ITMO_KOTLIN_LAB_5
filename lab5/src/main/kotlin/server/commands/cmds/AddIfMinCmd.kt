package server.commands.cmds

import share.commands.util.CommandResult
import share.data.movie.Movie
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

class AddIfMinCmd(movie: Movie, user: User, storage: MainStorage)
    : OneArgumentStorageCmd(movie, storage, "add_if_min", user, CommandType.ADD_IF_MIN) {
    override fun execute(): CommandResult {
        if(storage.addIfMin(movie)){
            return CommandResult(false, "Элемент был успешно добавлен")
        }
        return CommandResult(false, "Элемент не был добавлен")
    }
}