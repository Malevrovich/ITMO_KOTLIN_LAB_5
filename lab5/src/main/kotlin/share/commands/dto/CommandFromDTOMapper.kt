package share.commands.dto

import server.commands.cmds.Command
import share.commands.dto.CommandDTO
import share.executor.Executor

interface CommandFromDTOMapper {
    fun fromDTO(dto: CommandDTO, executor: Executor): Command
}