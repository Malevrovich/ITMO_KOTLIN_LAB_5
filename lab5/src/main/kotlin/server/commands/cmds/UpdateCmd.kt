package server.commands.cmds

import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.data.movie.Movie
import share.data.user.User

class UpdateCmd(val id: Int,
                movie: Movie,
                mainStorage: MainStorage,
                user: User
)
    : OneArgumentStorageCmd(movie, mainStorage, "update", user, CommandType.UPDATE){

    override fun execute(): CommandResult {
        return try {
            storage.update(id, movie, user)
            CommandResult(false, "Элемент с id = $id был успешно обновлен")
        } catch (e: NoSuchElementException){
            CommandResult(false, e.message ?: "")
        }
    }
}
