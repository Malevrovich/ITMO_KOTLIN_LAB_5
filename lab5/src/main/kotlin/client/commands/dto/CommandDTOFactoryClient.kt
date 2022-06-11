package client.commands.dto

import client.auth.LoginManager
import share.commands.dto.CommandDTO
import share.commands.dto.CommandType
import share.data.movie.Movie

class CommandDTOFactoryClient(val loginManager: LoginManager): CommandDTOFactory {
    override fun buildHelp(): CommandDTO {
        return CommandDTO(CommandType.HELP, loginManager.getCurrentSession())
    }

    override fun buildInfo(): CommandDTO {
        return CommandDTO(CommandType.INFO, loginManager.getCurrentSession())
    }

    override fun buildShow(): CommandDTO {
        return CommandDTO(CommandType.SHOW, loginManager.getCurrentSession())
    }

    override fun buildUpdate(id: Int, el: Movie): CommandDTO {
        return CommandDTO(CommandType.UPDATE, loginManager.getCurrentSession(), listOf(el), listOf(id))
    }

    override fun buildAdd(el: Movie): CommandDTO {
        return CommandDTO(CommandType.ADD, loginManager.getCurrentSession(), listOf(el))
    }

    override fun buildRemove(id: Int): CommandDTO {
        return CommandDTO(CommandType.REMOVE, loginManager.getCurrentSession(), int_args = listOf(id))
    }

    override fun buildClear(): CommandDTO {
        return CommandDTO(CommandType.CLEAR, loginManager.getCurrentSession())
    }

    override fun buildSave(): CommandDTO {
        return CommandDTO(CommandType.SAVE, loginManager.getCurrentSession())
    }

    override fun buildExecuteFile(filename: String): CommandDTO {
        return CommandDTO(CommandType.EXECUTE_FILE, loginManager.getCurrentSession(), string_args = listOf(filename))
    }

    override fun buildExit(): CommandDTO {
        return CommandDTO(CommandType.EXIT, loginManager.getCurrentSession())
    }

    override fun buildAddIfMin(el: Movie): CommandDTO {
        return CommandDTO(CommandType.ADD_IF_MIN, loginManager.getCurrentSession(), listOf(el))
    }

    override fun buildRemoveGreater(el: Movie): CommandDTO {
        return CommandDTO(CommandType.REMOVE_GREATER, loginManager.getCurrentSession(), listOf(el))
    }

    override fun buildHistory(): CommandDTO {
        return CommandDTO(CommandType.HISTORY, loginManager.getCurrentSession())
    }

    override fun buildSumOfLength(): CommandDTO {
        return CommandDTO(CommandType.SUM_OF_LENGTH, loginManager.getCurrentSession())
    }

    override fun buildPrintUniqueGenre(): CommandDTO {
        return CommandDTO(CommandType.PRINT_UNIQUE_GENRE, loginManager.getCurrentSession())
    }

    override fun buildPrintFieldDescendingScreenwriter(): CommandDTO {
        return CommandDTO(CommandType.PRINT_FIELD_DESCENDING_SCREENWRITER, loginManager.getCurrentSession())
    }

    override fun buildGetAll(): CommandDTO {
        return CommandDTO(CommandType.GET_ALL, loginManager.getCurrentSession())
    }

    override fun buildDisconnect(): CommandDTO {
        return CommandDTO(CommandType.DISCONNECT, loginManager.getCurrentSession())
    }
}