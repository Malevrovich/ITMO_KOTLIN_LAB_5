package commands

import commands.util.CommandResult
import storage.Storage

class PrintUniqueGenreCmd(storage: Storage) : StorageCmd(storage, "print_unique_genre") {
    override fun execute(): CommandResult {
        val res = storage.getUniqueGenre().joinToString { it.name }
        return CommandResult(false, res)
    }
}