package commands

import commands.util.CommandResult
import storage.Storage

class PrintFieldDescendingScreenwriterCmd(storage: Storage)
    : StorageCmd(storage, "print_field_descending_screenwriter") {
    override fun execute(): CommandResult {
        val res = storage.getDescendingScreenwriters().joinToString("\n") { it.name }
        return CommandResult(false, res)
    }

}