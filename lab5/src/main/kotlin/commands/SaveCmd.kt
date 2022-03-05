package commands

import commands.util.CommandResult
import storage.StorageKeeper

class SaveCmd(private val storageKeeper: StorageKeeper): Command("save") {
    override fun execute(): CommandResult {
        return try {
            storageKeeper.save()
            CommandResult(false, "Коллекция была успешно сохранена")
        } catch (e: Exception){
            CommandResult(false, "Невозможно сохранить коллекцию: ${e.message}")
        }
    }
}