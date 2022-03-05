package executor

import commands.Command
import java.io.InputStream
import java.io.OutputStream

interface StreamExecutor {
    fun execute(_inp: InputStream, _out: OutputStream, _executor: Executor? = null): List<Command>
}