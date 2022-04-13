package share.commands.util

import share.commands.Command
import share.user_io.user_reader.UserReader
import share.user_io.user_writer.UserWriter

interface CommandReader {
    fun readCommand(inp: UserReader, out: UserWriter): CommandDTO
}