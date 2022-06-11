package client.commands.dto

import server.commands.cmds.Command
import server.commands.cmds.ExecuteFileCmd
import server.commands.cmds.ExitCmd
import server.commands.cmds.HelpCmd
import share.commands.dto.CommandDTO
import share.commands.dto.CommandFromDTOMapper
import share.commands.dto.CommandType
import share.executor.Executor
import share.executor.stream_executor.StreamExecutorKeeper
import share.io.input.InputFactory
import share.io.output.OutputFactory

class CommandFromDTOMapperClient(val streamExecutorKeeper: StreamExecutorKeeper,
                                 val outputFactory: OutputFactory,
                                 val inputFactory: InputFactory
): CommandFromDTOMapper {
    override fun fromDTO(dto: CommandDTO, executor: Executor): Command {
        try{
            return when(dto.type){
                CommandType.EXECUTE_FILE
                        -> ExecuteFileCmd(dto.string_args[0], streamExecutorKeeper.currentStreamExecutor,
                                                                        outputFactory, inputFactory)

                CommandType.EXIT -> ExitCmd()
                CommandType.HELP -> HelpCmd()
                else -> throw IllegalArgumentException("ClientFromDTOMapper couldn't cast server commands")
            }
        } catch (e: IndexOutOfBoundsException){
            throw IllegalArgumentException("Wrong DTO structure")
        }
    }
}