package server.commands.cmds

import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.data.user.User
import share.util.NoPermissionsException

class RemoveCmd(val id: Int, storage: MainStorage, user: User)
    : StorageCmd(storage, "remove", user, CommandType.REMOVE) {
    override fun execute(): CommandResult {
        return try{
            storage.remove(id, user)
            CommandResult(false, "Элемент с id = $id был успешно удален")
        } catch (e: NoPermissionsException) {
            CommandResult(false, e.message ?: "Нет доступа")
        }
    }
}