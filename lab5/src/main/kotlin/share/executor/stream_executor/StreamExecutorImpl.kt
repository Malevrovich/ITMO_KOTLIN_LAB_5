package share.executor.stream_executor

import share.commands.util.CommandDTO
import share.commands.util.CommandReader
import share.commands.util.CommandResult
import share.executor.Executor
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter
import share.util.ParseException
import java.io.EOFException
import java.io.IOException

class StreamExecutorImpl(
    private val commandReader: CommandReader,
    private val executor: Executor
): StreamExecutor {

    override fun execute(userReader: UserReader, userWriter: UserWriter): List<CommandDTO> {
        while(userReader.hasNextLine()){
            val res: CommandResult = try{
                val cmd = commandReader.readCommand(userReader, userWriter)
                executor.execute(cmd)
            } catch (e: ParseException){
                userWriter.println(e.message)
                continue
            } catch (e: EOFException){
                CommandResult(true, "Входной поток был прерван")
            } catch (e: IOException) {
                CommandResult(true, "Входной поток был прерван")
            }

            userWriter.println(res.out)
            if(res.stop){
                break
            }
        }

        return executor.getHistory()
    }
}