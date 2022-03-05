package commands

import commands.util.CommandResult

class ExitCmd: Command("exit") {
    override fun execute(): CommandResult {
        return CommandResult(true)
    }
}