package share.user_io.user_writer

import java.io.OutputStream

interface UserWriterFactory {
    fun buildUserWriter(outputStream: OutputStream): UserWriter
}