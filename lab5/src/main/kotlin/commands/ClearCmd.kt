package commands

import commands.util.CommandResult
import storage.Storage

class ClearCmd(storage: Storage): StorageCmd(storage, "clear") {
    override fun execute(): CommandResult {
        storage.clear()
        return CommandResult(false, "Хранилище было успешно очищено")
    }
}