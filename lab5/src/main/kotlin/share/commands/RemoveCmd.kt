package share.commands

import share.commands.util.CommandResult
import server.storage.Storage
import share.commands.util.CommandType

class RemoveCmd(val id: Int, storage: Storage)
    : StorageCmd(storage, "remove", CommandType.REMOVE) {
    override fun execute(): CommandResult {
        storage.remove(id)
        return CommandResult(false, "Элемент с id = $id был успешно удален")
    }
}