package share.io.output.logged

import share.io.output.Output
import share.io.output.OutputFactory
import java.io.OutputStream

class LoggedOutputFactory(val outputFactory: OutputFactory): OutputFactory {
    override fun buildOutput(outputStream: OutputStream): Output {
        return LoggedOutput(outputFactory.buildOutput(outputStream))
    }

    override fun buildOutput(output: Output): Output {
        return LoggedOutput(outputFactory.buildOutput(output))
    }
}