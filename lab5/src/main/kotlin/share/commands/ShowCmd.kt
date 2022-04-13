package share.commands

import share.commands.util.CommandResult
import server.storage.Storage
import share.commands.util.CommandType

class ShowCmd(storage: Storage)
    : StorageCmd(storage, "show", CommandType.SHOW) {
    override fun execute(): CommandResult {
        val s = if(storage.isEmpty()){
            "...empty..."
        } else{
            storage.getAll().joinToString("\n") { it.toString() }
        }

        return CommandResult(false, s)
    }

}