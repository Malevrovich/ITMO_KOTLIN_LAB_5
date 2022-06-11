package server.commands.cmds

import share.commands.util.CommandResult
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

class ShowCmd(storage: MainStorage, user: User)
    : StorageCmd(storage, "show", user, CommandType.SHOW) {
    override fun execute(): CommandResult {
        val s = if(storage.isEmpty()){
            "...empty..."
        } else{
            storage.getAll().joinToString("\n") { it.toString() }
        }

        return CommandResult(false, s)
    }

}