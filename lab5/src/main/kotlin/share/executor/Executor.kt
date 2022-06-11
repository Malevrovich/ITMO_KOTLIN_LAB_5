package share.executor

import share.commands.dto.CommandDTO
import share.commands.util.CommandResult

interface Executor {
    fun execute(cmd: CommandDTO): CommandResult

    fun execute(cmdList: List<CommandDTO>): List<CommandResult>

    fun getHistory(): List<CommandDTO>
}