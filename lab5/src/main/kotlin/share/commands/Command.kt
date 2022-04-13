package share.commands

import share.commands.util.CommandResult
import kotlinx.serialization.Serializable
import share.commands.util.CommandType

@Serializable
abstract class Command(open val name: String, open val type: CommandType) {
    abstract fun execute(): CommandResult
}