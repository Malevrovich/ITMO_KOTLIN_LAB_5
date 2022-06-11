package server.commands.dto

import server.commands.cmds.*
import server.storage.main.MainStorage
import server.storage.storage_keeper.StorageKeeper
import share.commands.dto.CommandDTO
import share.commands.dto.CommandFromDTOMapper
import share.commands.dto.CommandType
import share.executor.Executor
import share.io.input.InputFactory
import share.io.output.OutputFactory

class CommandFromDTOMapperServer(
    val storage: MainStorage,
    val storageKeeper: StorageKeeper,
    val inputFactory: InputFactory,
    val outputFactory: OutputFactory
)
    : CommandFromDTOMapper {
    override fun fromDTO(dto: CommandDTO, executor: Executor): Command {
        try{
            return when(dto.type){
                CommandType.ADD -> AddCmd(dto.movie_args[0], dto.user, storage)
                CommandType.ADD_IF_MIN -> AddIfMinCmd(dto.movie_args[0], dto.user, storage)
                CommandType.CLEAR -> ClearCmd(storage, dto.user)

                CommandType.EXIT -> ExitCmd()
                CommandType.HELP -> HelpCmd()
                CommandType.HISTORY -> HistoryCmd(executor)
                CommandType.INFO -> InfoCmd(storage, dto.user)

                CommandType.PRINT_FIELD_DESCENDING_SCREENWRITER
                    -> PrintFieldDescendingScreenwriterCmd(storage, dto.user)

                CommandType.PRINT_UNIQUE_GENRE -> PrintUniqueGenreCmd(storage, dto.user)
                CommandType.REMOVE -> RemoveCmd(dto.int_args[0], storage, dto.user)
                CommandType.REMOVE_GREATER -> RemoveGreaterCmd(dto.movie_args[0], storage, dto.user)
                CommandType.SAVE -> SaveCmd(storageKeeper)
                CommandType.SHOW -> ShowCmd(storage, dto.user)
                CommandType.GET_ALL -> GetAllCmd(storage)
                CommandType.SUM_OF_LENGTH -> SumOfLengthCmd(storage, dto.user)
                CommandType.UPDATE -> UpdateCmd(dto.int_args[0], dto.movie_args[0], storage, dto.user)
                else -> throw IllegalArgumentException("Unsupported type of command: " + dto.type.name)
            }
        } catch (e: IndexOutOfBoundsException){
            throw IllegalArgumentException("Wrong DTO structure")
        }
    }
}