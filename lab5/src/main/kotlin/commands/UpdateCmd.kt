package commands

import commands.util.CommandResult
import data.movie.Movie
import storage.Storage

class UpdateCmd(private val id: Int,
                movie: Movie,
                storage: Storage): OneArgumentStorageCmd(movie, storage, "update"){

    override fun execute(): CommandResult {
        return try {
            storage.update(id, movie)
            CommandResult(false, "Элемент с id = $id был успешно обновлен")
        } catch (e: NoSuchElementException){
            CommandResult(false, e.message ?: "")
        }
    }
}
