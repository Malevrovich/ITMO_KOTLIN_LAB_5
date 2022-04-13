package share.commands

import share.commands.util.CommandResult
import server.storage.Storage
import share.commands.util.CommandType

class PrintUniqueGenreCmd(storage: Storage)
    : StorageCmd(storage, "print_unique_genre", CommandType.PRINT_UNIQUE_GENRE) {
    override fun execute(): CommandResult {
        val res = storage.getUniqueGenre().joinToString { it.name }
        return CommandResult(false, res)
    }
}