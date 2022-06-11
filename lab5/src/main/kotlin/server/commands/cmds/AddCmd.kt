package server.commands.cmds

import share.commands.util.CommandResult
import share.data.movie.Movie
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

class AddCmd(movie: Movie, user: User, storage: MainStorage)
    : OneArgumentStorageCmd(movie, storage, "add", user, CommandType.ADD) {
    override fun execute(): CommandResult {
        if(storage.add(movie)) {
            return CommandResult(false, "Элемент был успешно добавлен")
        }
        return CommandResult(false, "Элемент с таким id уже существует, используйте update")
    }
}