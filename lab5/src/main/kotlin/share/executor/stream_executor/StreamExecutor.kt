package share.executor.stream_executor

import share.commands.dto.CommandDTO
import share.io.input.Input
import share.io.output.Output

interface StreamExecutor {
    fun execute(input: Input, output: Output): List<CommandDTO>
}