package server.commands.cmds

import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.data.user.User

class ClearCmd(storage: MainStorage, user: User)
    : StorageCmd(storage, "clear", user, CommandType.CLEAR) {
    override fun execute(): CommandResult {
        storage.clear(user)
        return CommandResult(false, "Хранилище было успешно очищено")
    }
}