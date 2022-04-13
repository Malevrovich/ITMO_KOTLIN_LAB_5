package share.user_io.user_writer

import share.user_io.user_writer.UserWriter
import java.io.OutputStream
import java.io.PrintStream

class PrintUserWriter(outputStream: OutputStream): UserWriter {
    private val printStream = PrintStream(outputStream)

    override fun println(x: Any?) {
        printStream.println(x)
    }

    override fun print(x: Any?) {
        printStream.print(x)
    }
}