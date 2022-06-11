package share.commands.dto

import server.commands.cmds.*
import share.data.user.User

class CommandToDTOMapperImpl: CommandToDTOMapper {
    override fun toDTO(cmd: Command): CommandDTO {
        val defaultUser = User("login", "password".toByteArray(Charsets.UTF_8))
        return when(cmd){
            is AddCmd -> CommandDTO(cmd.type, cmd.user, listOf(cmd.movie))
            is AddIfMinCmd -> CommandDTO(cmd.type, cmd.user, listOf(cmd.movie))
            is ClearCmd -> CommandDTO(cmd.type, cmd.user)
            is ExecuteFileCmd -> CommandDTO(cmd.type, defaultUser, string_args = listOf(cmd.filename))
            is ExitCmd -> CommandDTO(cmd.type, defaultUser)
            is HelpCmd -> CommandDTO(cmd.type, defaultUser)
            is InfoCmd -> CommandDTO(cmd.type, cmd.user)
            is HistoryCmd -> CommandDTO(cmd.type, defaultUser)
            is PrintFieldDescendingScreenwriterCmd -> CommandDTO(cmd.type, cmd.user )
            is PrintUniqueGenreCmd -> CommandDTO(cmd.type, cmd.user )
            is RemoveCmd -> CommandDTO(cmd.type, cmd.user , int_args = listOf(cmd.id))
            is RemoveGreaterCmd -> CommandDTO(cmd.type, cmd.user , listOf(cmd.movie))
            is SaveCmd -> CommandDTO(cmd.type, defaultUser)
            is ShowCmd -> CommandDTO(cmd.type, cmd.user )
            is SumOfLengthCmd -> CommandDTO(cmd.type, cmd.user )
            is UpdateCmd -> CommandDTO(cmd.type, cmd.user , listOf(cmd.movie), listOf(cmd.id))
            else -> throw IllegalArgumentException()
        }
    }
}