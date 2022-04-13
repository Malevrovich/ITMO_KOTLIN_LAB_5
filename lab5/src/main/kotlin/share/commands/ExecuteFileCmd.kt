package share.commands

import share.commands.util.CommandResult
import share.executor.stream_executor.StreamExecutor
import share.commands.util.CommandType
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class ExecuteFileCmd(val filename: String,
                     private val streamExecutor: StreamExecutor
): Command("execute_file", CommandType.EXECUTE_FILE) {
    override fun execute(): CommandResult {
        TODO("NOT YET IMPLEMENTED")
//        return try {
//            FileInputStream(filename).use {
//                inp -> streamExecutor.execute(inp, System.out)
//            }
//
//            CommandResult(false, "Файл $filename был успешно исполнен")
//        } catch (e: FileNotFoundException){
//            CommandResult(false, "Файл $filename не существует")
//        } catch (e: IOException) {
//            CommandResult(false, "Невозможно прочитать из $filename: ${e.message}")
//        }
    }
}