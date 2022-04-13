package share.commands

import share.commands.util.CommandResult
import server.storage.Storage
import share.commands.util.CommandType

class PrintFieldDescendingScreenwriterCmd(storage: Storage)
    : StorageCmd(storage,
                "print_field_descending_screenwriter",
                CommandType.PRINT_FIELD_DESCENDING_SCREENWRITER) {
    override fun execute(): CommandResult {
        val res = storage.getDescendingScreenwriters().joinToString("\n") { it.name }
        return CommandResult(false, res)
    }

}