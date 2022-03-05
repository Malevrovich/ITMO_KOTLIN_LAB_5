package commands.util

import commands.Command
import data.movie.Movie
import data.movie.MovieReader
import org.kodein.di.DI
import org.kodein.di.instance
import util.ParseException
import java.io.PrintStream
import java.util.*

class CommandReaderImpl(di: DI): CommandReader {
    private val factory: CommandFactory by di.instance()
    private val movieReader: MovieReader by di.instance()

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

    override fun readCommand(inp: Scanner, out: PrintStream): Command {
        fun askMovie(): Movie {
            return movieReader.askMovie(inp, out)
        }

        inp.useDelimiter("\n")

        val s = inp.nextLine()

        if(s.isBlank()){
            throw ParseException("Команда не может быть пустой строкой")
        }

        val arr = s.split(" ")

        fun getId(): Int{
            return getId(arr, arr[0])
        }

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
            "add" -> factory.buildAdd(askMovie())
            "update" -> factory.buildUpdate(getId(), askMovie())
            "remove_by_id" -> factory.buildRemove(getId())
            "execute_script" -> {
                checkSize(arr, 2, arr[0])
                factory.buildExecuteFile(arr[1])
            }
            "add_if_min" -> factory.buildAddIfMin(askMovie())
            "remove_greater" -> factory.buildRemoveGreater(askMovie())
            else -> throw ParseException("Неизвестная команда ${arr[0]}")
        }
    }
}