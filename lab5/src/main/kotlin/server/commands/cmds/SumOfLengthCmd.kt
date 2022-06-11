package server.commands.cmds

import share.commands.util.CommandResult
import server.storage.main.MainStorage
import share.commands.dto.CommandType
import share.data.user.User

class SumOfLengthCmd(storage: MainStorage, user: User)
    : StorageCmd(storage, "sum_of_length", user, CommandType.SUM_OF_LENGTH) {
    override fun execute(): CommandResult {
        val res = storage.sumOfLength()
        return CommandResult(false, "Сумма значений поля length = $res")
    }
}