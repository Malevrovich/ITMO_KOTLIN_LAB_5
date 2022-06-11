package share.io.output.command_result

import share.io.output.Output
import share.io.output.OutputFactory
import java.io.OutputStream

class CommandOutputFactory(val outputFactory: OutputFactory): OutputFactory{
    override fun buildOutput(outputStream: OutputStream): Output {
        return CommandResultOutput(outputFactory.buildOutput(outputStream))
    }

    override fun buildOutput(output: Output): Output {
        return CommandResultOutput(outputFactory.buildOutput(output))
    }
}