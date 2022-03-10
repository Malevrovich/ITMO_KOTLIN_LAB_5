package commands

import commands.util.CommandResult
import executor.Executor
import executor.StreamExecutor
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class ExecuteFileCmd(private val filename: String,
                     private val streamExecutor: StreamExecutor): Command("execute_file") {
    override fun execute(): CommandResult {
        return try {
            FileInputStream(filename).use {
                inp -> streamExecutor.execute(inp, System.out)
            }

            CommandResult(false, "Файл $filename был успешно исполнен")
        } catch (e: FileNotFoundException){
            CommandResult(false, "Файл $filename не существует")
        } catch (e: IOException) {
            CommandResult(false, "Невозможно прочитать из $filename: ${e.message}")
        }
    }
}