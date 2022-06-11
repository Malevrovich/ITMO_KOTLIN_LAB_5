package server.commands.cmds

import share.commands.util.CommandResult
import share.commands.dto.CommandType

class ExitCmd: Command("exit", CommandType.EXIT) {
    override fun execute(): CommandResult {
        return CommandResult(true)
    }
}