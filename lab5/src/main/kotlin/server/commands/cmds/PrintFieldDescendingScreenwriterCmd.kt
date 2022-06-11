package server.commands.cmds

import share.commands.util.CommandResult
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

class PrintFieldDescendingScreenwriterCmd(mainStorage: MainStorage, user: User)
    : StorageCmd(mainStorage,
                "print_field_descending_screenwriter",
                user,
                CommandType.PRINT_FIELD_DESCENDING_SCREENWRITER) {
    override fun execute(): CommandResult {
        val res = storage.getDescendingScreenwriters().joinToString("\n") { it.name }
        return CommandResult(false, res)
    }

}