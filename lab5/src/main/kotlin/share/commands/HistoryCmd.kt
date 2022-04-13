package share.commands

import share.commands.util.CommandResult
import share.commands.util.CommandType
import share.executor.Executor

class HistoryCmd(private val executor: Executor): Command("history", CommandType.HISTORY) {
    override fun execute(): CommandResult {
        return CommandResult(false, executor.getHistory().joinToString(", ") { it.type.name })
    }
}