package server.user_io.user_writer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import share.commands.util.CommandResult
import share.user_io.user_writer.UserWriter
import java.io.OutputStream
import java.io.PrintStream

class ConnectionUserWriter(val outputStream: OutputStream): UserWriter {
    override fun println(x: Any?) {
        val msg = x.toString()
        val res = CommandResult(false, msg)
        PrintStream(outputStream, true, "UTF-8").println(Json.encodeToString(res))
    }

    override fun print(x: Any?) {
        val msg = x.toString()
        val res = CommandResult(false, msg)
        PrintStream(outputStream, true, "UTF-8").print(Json.encodeToString(res))
    }
}