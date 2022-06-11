package share.commands.dto

import server.commands.cmds.Command
import share.commands.dto.CommandDTO

interface CommandToDTOMapper {
    fun toDTO(cmd: Command): CommandDTO
}