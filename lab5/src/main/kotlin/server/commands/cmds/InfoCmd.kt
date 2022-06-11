package server.commands.cmds

import share.commands.util.CommandResult
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

class InfoCmd(override val storage: MainStorage, override val user: User)
    : StorageCmd(storage, "info", user, CommandType.INFO){
    override fun execute(): CommandResult {
        return CommandResult(false, "Время инициализации: ${storage.initTime}\n" +
                "Количество элементов: ${storage.getSize()}")
    }
}