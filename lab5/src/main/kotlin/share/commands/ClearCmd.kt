package share.commands

import share.commands.util.CommandResult
import server.storage.Storage
import share.commands.util.CommandType

class ClearCmd(storage: Storage)
    : StorageCmd(storage, "clear", CommandType.CLEAR) {
    override fun execute(): CommandResult {
        storage.clear()
        return CommandResult(false, "Хранилище было успешно очищено")
    }
}