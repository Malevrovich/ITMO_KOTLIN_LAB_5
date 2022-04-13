package share.commands.util

import share.commands.Command

interface CommandToDTOMapper {
    fun toDTO(cmd: Command): CommandDTO
}