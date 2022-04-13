package share.commands

import share.commands.util.CommandResult
import share.data.movie.Movie
import server.storage.Storage
import share.commands.util.CommandType

class UpdateCmd(val id: Int,
                movie: Movie,
                storage: Storage)
    : OneArgumentStorageCmd(movie, storage, "update", CommandType.UPDATE){

    override fun execute(): CommandResult {
        return try {
            storage.update(id, movie)
            CommandResult(false, "Элемент с id = $id был успешно обновлен")
        } catch (e: NoSuchElementException){
            CommandResult(false, e.message ?: "")
        }
    }
}
