package server.commands.cmds

import share.commands.dto.CommandType
import share.commands.util.CommandResult
import share.executor.stream_executor.StreamExecutor
import share.io.input.InputFactory
import share.io.output.OutputFactory
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class ExecuteFileCmd(val filename: String,
                     val streamExecutor: StreamExecutor,
                     val outputFactory: OutputFactory,
                     val inputFactory: InputFactory
): Command("execute_file", CommandType.EXECUTE_FILE) {
    override fun execute(): CommandResult {
        return try {
            val outStream = ByteArrayOutputStream()

            FileInputStream(filename).use {
                inp -> streamExecutor.execute(inputFactory.buildInput(inp),
                                                outputFactory.buildOutput(outStream))
            }

            CommandResult(false,
                outStream.toString("UTF-8") + "\nФайл $filename был успешно исполнен")
        } catch (e: FileNotFoundException){
            CommandResult(false, "Файл $filename не существует")
        } catch (e: IOException) {
            CommandResult(false, "Невозможно прочитать из $filename: ${e.message}")
        }
    }
}