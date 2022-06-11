package share.io.output

import java.io.OutputStream

interface OutputFactory {
    fun buildOutput(outputStream: OutputStream): Output
    fun buildOutput(output: Output): Output
}