package server.commands

import share.commands.Command
import share.commands.util.CommandDTO
import share.executor.Executor

interface CommandFromDTOMapper {
    fun fromDTO(dto: CommandDTO, executor: Executor): Command
}