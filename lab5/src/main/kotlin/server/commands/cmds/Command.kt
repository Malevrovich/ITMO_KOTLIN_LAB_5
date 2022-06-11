package server.commands.cmds

import kotlinx.serialization.Serializable
import share.commands.dto.CommandType
import share.commands.util.CommandResult

@Serializable
abstract class Command(open val name: String, open val type: CommandType) {
    abstract fun execute(): CommandResult
}