package executor

import commands.Command
import commands.util.CommandFactoryConfigure
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

class StreamExecutorImpl(private val di: DI): StreamExecutor {
    private val defaultExecutor: Executor by di.instance()
    private val commandReader: CommandReader by di.instance()
    private val commandFactoryConfigure: CommandFactoryConfigure by di.instance()

    override fun execute(_inp: InputStream, _out: OutputStream, _executor: Executor?): List<Command> {
        val executor = _executor ?: defaultExecutor

        val inp = Scanner(_inp)
        val out = PrintStream(_out)

        while(inp.hasNextLine()){
            commandFactoryConfigure.currentExecutor = executor
            commandFactoryConfigure.streamExecutor = StreamExecutorImpl(di)

            val res: CommandResult = try{
                val cmd = commandReader.readCommand(inp, out) ?: continue
                executor.execute(cmd)
            } catch (e: ParseException){
                out.println(e.message)
                continue
            } catch (e: EOFException){
                CommandResult(true, "Входной поток был прерван")
            }

            out.println(res.out)
            if(res.stop){
                break
            }
        }

        return executor.getHistory()
    }
}