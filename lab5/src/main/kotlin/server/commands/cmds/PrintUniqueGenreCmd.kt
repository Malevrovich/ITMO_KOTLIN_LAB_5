package server.commands.cmds

import share.commands.util.CommandResult
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

class PrintUniqueGenreCmd(storage: MainStorage, user: User)
    : StorageCmd(storage, "print_unique_genre", user, CommandType.PRINT_UNIQUE_GENRE) {
    override fun execute(): CommandResult {
        val res = storage.getUniqueGenre().joinToString { it.name }
        return CommandResult(false, res)
    }
}