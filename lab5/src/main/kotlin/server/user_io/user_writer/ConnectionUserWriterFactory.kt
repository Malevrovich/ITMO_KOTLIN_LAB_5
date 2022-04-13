package server.user_io.user_writer

import share.user_io.user_writer.UserWriter
import share.user_io.user_writer.UserWriterFactory
import java.io.OutputStream

class ConnectionUserWriterFactory: UserWriterFactory {
    override fun buildUserWriter(outputStream: OutputStream): UserWriter {
        return ConnectionUserWriter(outputStream)
    }
}