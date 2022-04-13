package server.commands

import server.storage.Storage
import server.storage.StorageKeeper
import share.commands.*
import share.commands.util.CommandDTO
import share.commands.util.CommandType
import share.executor.Executor
import share.executor.stream_executor.StreamExecutorKeeper

class CommandFromDTOMapperImpl(
    val storage: Storage,
    val streamExecutorKeeper: StreamExecutorKeeper,
    val storageKeeper: StorageKeeper
)
    : CommandFromDTOMapper {
    override fun fromDTO(dto: CommandDTO, executor: Executor): Command {
        try{
            return when(dto.type){
                CommandType.ADD -> AddCmd(dto.movie_args[0], storage)
                CommandType.ADD_IF_MIN -> AddIfMinCmd(dto.movie_args[0], storage)
                CommandType.CLEAR -> ClearCmd(storage)
                CommandType.EXECUTE_FILE -> ExecuteFileCmd(dto.string_args[0], streamExecutorKeeper.currentStreamExecutor)
                CommandType.EXIT -> ExitCmd()
                CommandType.HELP -> HelpCmd()
                CommandType.HISTORY -> HistoryCmd(executor)
                CommandType.INFO -> InfoCmd(storage)
                CommandType.PRINT_FIELD_DESCENDING_SCREENWRITER -> PrintFieldDescendingScreenwriterCmd(storage)
                CommandType.PRINT_UNIQUE_GENRE -> PrintUniqueGenreCmd(storage)
                CommandType.REMOVE -> RemoveCmd(dto.int_args[0], storage)
                CommandType.REMOVE_GREATER -> RemoveGreaterCmd(dto.movie_args[0], storage)
                CommandType.SAVE -> SaveCmd(storageKeeper)
                CommandType.SHOW -> ShowCmd(storage)
                CommandType.SUM_OF_LENGTH -> SumOfLengthCmd(storage)
                CommandType.UPDATE -> UpdateCmd(dto.int_args[0], dto.movie_args[0], storage)
            }
        } catch (e: IndexOutOfBoundsException){
            throw IllegalArgumentException("Wrong DTO structure")
        }
    }
}