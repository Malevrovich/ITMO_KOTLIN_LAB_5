package client.commands

import share.commands.util.CommandDTO
import share.commands.util.CommandDTOFactory
import share.commands.util.CommandType
import share.data.movie.Movie

class CommandDTOFactoryImpl: CommandDTOFactory {
    override fun buildHelp(): CommandDTO {
        return CommandDTO(CommandType.HELP)
    }

    override fun buildInfo(): CommandDTO {
        return CommandDTO(CommandType.INFO)
    }

    override fun buildShow(): CommandDTO {
        return CommandDTO(CommandType.SHOW)
    }

    override fun buildUpdate(id: Int, el: Movie): CommandDTO {
        return CommandDTO(CommandType.UPDATE, listOf(el), listOf(id))
    }

    override fun buildAdd(el: Movie): CommandDTO {
        return CommandDTO(CommandType.ADD, listOf(el))
    }

    override fun buildRemove(id: Int): CommandDTO {
        return CommandDTO(CommandType.REMOVE, int_args = listOf(id))
    }

    override fun buildClear(): CommandDTO {
        return CommandDTO(CommandType.CLEAR)
    }

    override fun buildSave(): CommandDTO {
        return CommandDTO(CommandType.SAVE)
    }

    override fun buildExecuteFile(filename: String): CommandDTO {
        return CommandDTO(CommandType.EXECUTE_FILE, string_args = listOf(filename))
    }

    override fun buildExit(): CommandDTO {
        return CommandDTO(CommandType.EXIT)
    }

    override fun buildAddIfMin(el: Movie): CommandDTO {
        return CommandDTO(CommandType.ADD_IF_MIN, listOf(el))
    }

    override fun buildRemoveGreater(el: Movie): CommandDTO {
        return CommandDTO(CommandType.REMOVE_GREATER, listOf(el))
    }

    override fun buildHistory(): CommandDTO {
        return CommandDTO(CommandType.HISTORY)
    }

    override fun buildSumOfLength(): CommandDTO {
        return CommandDTO(CommandType.SUM_OF_LENGTH)
    }

    override fun buildPrintUniqueGenre(): CommandDTO {
        return CommandDTO(CommandType.PRINT_UNIQUE_GENRE)
    }

    override fun buildPrintFieldDescendingScreenwriter(): CommandDTO {
        return CommandDTO(CommandType.PRINT_FIELD_DESCENDING_SCREENWRITER)
    }
}