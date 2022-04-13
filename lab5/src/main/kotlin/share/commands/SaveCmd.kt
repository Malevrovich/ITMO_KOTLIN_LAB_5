package share.commands

import share.commands.util.CommandResult
import server.storage.StorageKeeper
import share.commands.util.CommandType

class SaveCmd(private val storageKeeper: StorageKeeper)
    : Command("save", CommandType.SAVE) {
    override fun execute(): CommandResult {
        return try {
            storageKeeper.save()
            CommandResult(false, "Коллекция была успешно сохранена")
        } catch (e: Exception){
            CommandResult(false, "Невозможно сохранить коллекцию: ${e.message}")
        }
    }
}