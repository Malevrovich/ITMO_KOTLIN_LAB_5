package client.commands

import client.data.movie.MovieReader
import share.commands.util.CommandDTO
import share.commands.util.CommandDTOFactory
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter
import share.commands.util.CommandReader
import share.util.ParseException

class CommandReaderImpl(
    private val factory: CommandDTOFactory,
    private val movieReader: MovieReader
): CommandReader {

    private fun checkSize(arr: List<String>, size: Int, name: String){
        if(arr.size != size){
            throw ParseException("Команда $name должна содержать хотя бы $size слов")
        }
    }

    private fun getId(arr: List<String>, name: String, pos: Int = 1): Int{
        checkSize(arr, pos + 1, name)
        try{
            return arr[pos].toInt()
        } catch (e: NumberFormatException){
            throw ParseException("Команда $name должна принимать в качестве ${pos - 1} аргумента целое число")
        }
    }

    override fun readCommand(inp: UserReader, out: UserWriter): CommandDTO {
        val s = inp.nextLine()

        if(s.isBlank()){
            throw ParseException("Команда не может быть пустой строкой")
        }

        val arr = s.split(" ")

        return when(arr[0]){
            "help" -> factory.buildHelp()
            "info" -> factory.buildInfo()
            "show" -> factory.buildShow()
            "clear" -> factory.buildClear()
            "save" -> factory.buildSave()
            "exit" -> factory.buildExit()
            "history" -> factory.buildHistory()
            "sum_of_length" -> factory.buildSumOfLength()
            "print_unique_genre" -> factory.buildPrintUniqueGenre()
            "print_field_descending_screenwriter" -> factory.buildPrintFieldDescendingScreenwriter()
            "add" -> factory.buildAdd(movieReader.askMovie(inp, out))
            "update" -> factory.buildUpdate(getId(arr, arr[0]), movieReader.askMovie(inp, out))
            "remove_by_id" -> factory.buildRemove(getId(arr, arr[0]))
            "execute_script" -> {
                checkSize(arr, 2, arr[0])
                factory.buildExecuteFile(arr[1])
            }
            "add_if_min" -> factory.buildAddIfMin(movieReader.askMovie(inp, out))
            "remove_greater" -> factory.buildRemoveGreater(movieReader.askMovie(inp, out))
            else -> throw ParseException("Неизвестная команда ${arr[0]}")
        }
    }
}