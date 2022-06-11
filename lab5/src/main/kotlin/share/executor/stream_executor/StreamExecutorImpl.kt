package share.executor.stream_executor

import share.commands.dto.CommandDTO
import share.commands.util.CommandReader
import share.commands.util.CommandResult
import share.executor.Executor
import share.io.input.Input
import share.io.output.Output
import share.util.ParseException
import java.io.EOFException
import java.io.IOException

class StreamExecutorImpl(
    private val commandReader: CommandReader,
    private val executor: Executor
): StreamExecutor {

    override fun execute(input: Input, output: Output): List<CommandDTO> {
        while(true){
            val res: CommandResult = try{
                val cmd = commandReader.readCommand(input, output)
                executor.execute(cmd)
            } catch (e: ParseException){
                output.println(e.message)
                continue
            } catch (e: EOFException){
                CommandResult(true, "Входной поток был прерван")
            } catch (e: IOException) {
                CommandResult(true, "Входной поток был прерван")
            }

            output.println(res.out)
            if(res.stop){
                break
            }
        }

        return executor.getHistory()
    }
}