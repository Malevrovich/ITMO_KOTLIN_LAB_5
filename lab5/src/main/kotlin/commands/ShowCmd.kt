package commands

import commands.util.CommandResult
import storage.Storage

class ShowCmd(storage: Storage): StorageCmd(storage, "show") {
    override fun execute(): CommandResult {
        val s = if(storage.isEmpty()){
            "...empty..."
        } else{
            storage.getAll().joinToString("\n") { it.toString() }
        }

        return CommandResult(false, s)
    }

}