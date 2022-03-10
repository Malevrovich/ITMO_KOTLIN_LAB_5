package executor

import commands.Command
import java.io.InputStream
import java.io.OutputStream

interface StreamExecutor {
    fun execute(inputStream: InputStream, outputStream: OutputStream): List<Command>
}