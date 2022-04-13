package share.commands.util

import share.commands.*
import share.commands.util.CommandDTO
import share.commands.util.CommandToDTOMapper

class CommandToDTOMapperImpl: CommandToDTOMapper {
    override fun toDTO(cmd: Command): CommandDTO {
        return when(cmd){
            is AddCmd -> CommandDTO(cmd.type, listOf(cmd.movie))
            is AddIfMinCmd -> CommandDTO(cmd.type, listOf(cmd.movie))
            is ClearCmd -> CommandDTO(cmd.type)
            is ExecuteFileCmd -> CommandDTO(cmd.type, string_args = listOf(cmd.filename))
            is ExitCmd -> CommandDTO(cmd.type)
            is HelpCmd -> CommandDTO(cmd.type)
            is InfoCmd -> CommandDTO(cmd.type)
            is HistoryCmd -> CommandDTO(cmd.type)
            is PrintFieldDescendingScreenwriterCmd -> CommandDTO(cmd.type)
            is PrintUniqueGenreCmd -> CommandDTO(cmd.type)
            is RemoveCmd -> CommandDTO(cmd.type, int_args = listOf(cmd.id))
            is RemoveGreaterCmd -> CommandDTO(cmd.type, listOf(cmd.movie))
            is SaveCmd -> CommandDTO(cmd.type)
            is ShowCmd -> CommandDTO(cmd.type)
            is SumOfLengthCmd -> CommandDTO(cmd.type)
            is UpdateCmd -> CommandDTO(cmd.type, listOf(cmd.movie), listOf(cmd.id))
            else -> throw IllegalArgumentException()
        }
    }
}