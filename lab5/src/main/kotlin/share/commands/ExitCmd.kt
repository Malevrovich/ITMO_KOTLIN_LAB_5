package share.commands

import share.commands.util.CommandResult
import share.commands.util.CommandType

class ExitCmd: Command("exit", CommandType.EXIT) {
    override fun execute(): CommandResult {
        return CommandResult(true)
    }
}