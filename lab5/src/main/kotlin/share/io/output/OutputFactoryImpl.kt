package share.io.output

import java.io.OutputStream

class OutputFactoryImpl: OutputFactory {
    override fun buildOutput(outputStream: OutputStream): Output {
        return StreamOutput(outputStream)
    }

    override fun buildOutput(output: Output): Output {
        return output
    }
}