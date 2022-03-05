package executor

import commands.Command
import commands.util.CommandResult
import java.io.InputStream

interface Executor {
    fun execute(cmd: Command): CommandResult

    fun execute(cmdList: List<Command>): List<CommandResult>

    fun getHistory(): List<Command>
}