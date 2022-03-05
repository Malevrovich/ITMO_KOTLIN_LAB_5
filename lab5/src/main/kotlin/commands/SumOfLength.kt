package commands

import commands.util.CommandResult
import storage.Storage

class SumOfLength(storage: Storage) : StorageCmd(storage, "sum_of_length") {
    override fun execute(): CommandResult {
        val res = storage.sumOfLength()
        return CommandResult(false, "Сумма значений поля length = $res")
    }
}