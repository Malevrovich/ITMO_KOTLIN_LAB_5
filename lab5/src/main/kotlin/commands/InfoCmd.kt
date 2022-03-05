package commands

import commands.util.CommandResult
import storage.Storage

class InfoCmd(private val storage: Storage): Command("info"){
    override fun execute(): CommandResult {
        return CommandResult(false, "Время инициализации: ${storage.initTime}\n" +
                "Количество элементов: ${storage.getSize()}")
    }
}