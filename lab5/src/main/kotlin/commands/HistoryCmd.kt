package commands

import commands.util.CommandResult
import executor.Executor

class HistoryCmd(private val executor: Executor): Command("history") {
    override fun execute(): CommandResult {
        return CommandResult(false, executor.getHistory().joinToString(", ") { it.name })
    }
}