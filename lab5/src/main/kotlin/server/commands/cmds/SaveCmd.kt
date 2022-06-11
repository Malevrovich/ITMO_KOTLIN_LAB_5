package server.commands.cmds

import share.commands.util.CommandResult
import server.storage.storage_keeper.StorageKeeper
import share.commands.dto.CommandType

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