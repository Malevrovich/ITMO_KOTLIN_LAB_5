package share.commands

import share.commands.util.CommandResult
import server.storage.Storage
import share.commands.util.CommandType

class InfoCmd(private val storage: Storage): Command("info", CommandType.INFO){
    override fun execute(): CommandResult {
        return CommandResult(false, "Время инициализации: ${storage.initTime}\n" +
                "Количество элементов: ${storage.getSize()}")
    }
}