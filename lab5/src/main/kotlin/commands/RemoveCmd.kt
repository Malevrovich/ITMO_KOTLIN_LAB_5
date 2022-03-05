package commands

import commands.util.CommandResult
import storage.Storage

class RemoveCmd(private val id: Int, storage: Storage): StorageCmd(storage, "remove") {
    override fun execute(): CommandResult {
        storage.remove(id)
        return CommandResult(false, "Элемент с id = $id был успешно удален")
    }
}