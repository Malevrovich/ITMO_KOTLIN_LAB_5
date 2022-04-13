package share.executor.stream_executor

import share.commands.util.CommandDTO
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter

interface StreamExecutor {
    fun execute(userReader: UserReader, userWriter: UserWriter): List<CommandDTO>
}