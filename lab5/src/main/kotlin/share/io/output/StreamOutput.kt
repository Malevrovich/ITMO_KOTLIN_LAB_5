package share.io.output

import java.io.OutputStream

class StreamOutput(val outputStream: OutputStream): Output {
    val writer = outputStream.writer(Charsets.UTF_8)

    override fun print(x: Any?) {
        Thread.sleep(1000)
        writer.write(x.toString())
        writer.flush()
    }

    override fun println(x: Any?) {
        Thread.sleep(1000)
        writer.write(x.toString())
        writer.write("\n")
        writer.flush()
    }
}