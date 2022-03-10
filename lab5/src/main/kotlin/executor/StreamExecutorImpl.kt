package executor

import commands.Command
import commands.util.CommandReader
import commands.util.CommandResult
import org.kodein.di.DI
import org.kodein.di.instance
import util.ParseException
import java.io.EOFException
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.util.*

class StreamExecutorImpl(
    private val commandReader: CommandReader,
    private val executor: Executor
): StreamExecutor {

    override fun execute(inputStream: InputStream, outputStream: OutputStream): List<Command> {
        val inputScanner = Scanner(inputStream)
        val printStream = PrintStream(outputStream)

        while(inputScanner.hasNextLine()){
            val res: CommandResult = try{
                val cmd = commandReader.readCommand(inputScanner, printStream) ?: continue
                executor.execute(cmd)
            } catch (e: ParseException){
                printStream.println(e.message)
                continue
            } catch (e: EOFException){
                CommandResult(true, "Входной поток был прерван")
            }

            printStream.println(res.out)
            if(res.stop){
                break
            }
        }

        return executor.getHistory()
    }
}