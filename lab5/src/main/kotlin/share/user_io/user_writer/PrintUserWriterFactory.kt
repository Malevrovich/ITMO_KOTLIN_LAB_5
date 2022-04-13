package share.user_io.user_writer

import java.io.OutputStream

class PrintUserWriterFactory: UserWriterFactory {
    override fun buildUserWriter(outputStream: OutputStream): PrintUserWriter {
        return PrintUserWriter(outputStream)
    }
}