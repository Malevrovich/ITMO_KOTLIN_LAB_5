package share.commands

import share.commands.util.CommandResult
import server.storage.Storage
import share.commands.util.CommandType

class SumOfLengthCmd(storage: Storage)
    : StorageCmd(storage, "sum_of_length", CommandType.SUM_OF_LENGTH) {
    override fun execute(): CommandResult {
        val res = storage.sumOfLength()
        return CommandResult(false, "Сумма значений поля length = $res")
    }
}